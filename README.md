# 🌐 Rede Monitor

O Rede Monitor é um sistema web de monitoramento de dispositivos de rede. Esse projeto é desafiador porque cada empresa registrada nele pode ter dezenas, centenas ou mais dispositivos (roteadores, servidores, etc) monitorados 24 horas por dia executando simultaneamente. O que demanda investimento em escalabilidade de microsserviços, principalmente o de monitoramento.

## 💻 Tecnologias

- Java 25
- Spring Boot 4.0.0-M3
- React 19.1.1
- Postgresql 16.3
- Spring Scheduler com Thread Pool Task Scheduler
- Comando PING para envio de pacotes ICMP
- WebSockets
- React Bootstrap
- Swagger/OpenAPI
- Spring Security e Token JWT

## 🔒 Segurança e Autenticação

A segurança no backend envolve Spring Security e Token JWT.

### 🔓 Os Tokens

O Access token é armazenado em um cookie httponly e tem tempo de expiração de 5 minutos e seu payload contém as seguintes claims adicionais:

- <ins>**username**</ins>: como subject
- <ins>**roles**</ins>: papeis do usuário embutidos no token como array de strings

O refresh token é armazenado em um cookie httponly e tem tempo de expiração de 30 minutos e seu payload contem as seguintes claims adicionais:

- <ins>**username**</ins>: como subject

### 🛡️ O Filtro de Authorização

O filtro de autorização funciona da seguinte forma: Se for feita a requisição para o endpoint de login, então o cookie que é responsável por armazenar o token de acesso é removido e o fluxo segue para o controller e service de login. Caso contrário, o filtro de autorização extrai o token do cookie, valida, extrai o username como subject e os roles embutidos e carrega o objeto Authentication com username e os roles para a autorização do spring security funcionar. Caso o token seja inválido ou esteja expirado, o backend retorna uma mensagem de erro.

Antes de cada requisição chegar ao devido endpoint, ela passa pelo filtro de autorização e são executados os seguintes passos:

- A requisição chega ao filtro de autorização
- Se a requisição vier de outro microserviço, o token vem como header Authorization: Bearer
- Se a requisição vier do frontend, o token vem como cookie http only
- Se o token vier como cookie, ele é extraído e o header de Authorization: Bearer token é adicionado a requisição
- Então, são extraídos do token o username e os roles e o SecurityContextHolder é carregado com esses 
dados de autenticação para a autenticação e autorização do endpoints funcionar.

### 🔑 O Login

Durante a autenticação, acontece o seguinte: O frontend envia uma requisição para o endpoint de login. O service do endpoint de login busca o usuário, seus grupos e roles no banco de dados pelo username e senha e gera o access token e o refresh token. Então, gera os cookies httponly para o access token e o refresh token e os retorna para o navegador armazenar os cookies como httponly e os reenviar a cada nova requisição aos endpoints do backend.

### 🛡️ O interceptor de API no frontend e o Refresh Token

Quando o frontend envia uma requisição e o filtro de autorização retorna uma mensagem 401 ou 403, o interceptor de API captura a resposta e supõe que o token expirou. Então, gera uma nova requisição para o endpoint de refresh token para que um novo token seja gerado. Por sua vez, o service utilizado pelo endpoint de refresh token faz o seguinte:

- extrai o refresh token do cookie
- extrai o username do refresh token
- Busca o usuário pelo username
- Carrega os roles do usuário
- Gera o novo token de acesso
- Gera um novo cookie httponly com o token de acesso e retorna para o navegador
- Retorna um DTO de LoginResponse com o novo token, o username e o nome do usuário

Após isto, o interceptor de API recebe o LoginResponse, acessa o access token e seta o access token no AuthContext de Context API.

O access token armazenado no estado da aplicação via Context API é utilizado pelo websocket de atualização de detalhes do dispositivo.

### 📡 A autenticação e autorização via Websocket

O websocket tem um interceptor configurado, onde, o token de acesso deve ser extraído do cabeçalho de token de acesso bearer. Então, carrega o username extraído do token para o envio de mensagens para o usuário específico pelo username funcionar. Isso garante que apenas os usuários com o token válido e o devido username recebam a mensagem enviada.

### 👩‍💻 Configuração de Cors

A configuração de cors está no backend da seguinte forma:

- A origem é o host com porta do local onde o frontend estiver hospedado ou localhost:5173 para desenvolvimento.
- O mapping path tem o seguinte padrão: /** englobando todos os endpoints da API e do websocket
- Todos os cabeçalhos são permitidos
- Todos os métodos são permitidos
- As credenciais são permitidas, o que necessita do '*withCredentials: true*' nas requisições via axios

## 🔗 A comunicação via REST entre microserviços

Os microserviços se comunicam via REST ou messageria. Na comunicação via REST, é utilizado o RestClient para consumo da API do microserviço alvo. Cada microserviço integrado possui componentes de integração com os controllers e endpoints dos microserviços alvo. Os endpoints são configurados no application.properties, bem como o token utilizado para acesso a API do microserviço alvo. Um único token é compartilhado pelos microserviços para comunicação entre eles. Esse token não é gerado pela aplicação e é armazenado em local seguro. Isto é, como variável de ambiente dos containers dos microserviços. Esse token de microserviços tem o username: "microservice" e o role "microservice", necessário para acessar a API compartilhada. O token de microserviço também, teoricamente, não tem tempo de expiração. É vitalício. Dado que seu tempo de expiração em segundos corresponde ao valor inteiro máximo de 32 bits que deve corresponder a mais de um século.

## 🤖 O escalonador de réplicas do microserviço de monitoramento

O escalonador de réplicas gerencia as requisições de start de monitoramento de um dispositivo ou todos os
dispositivos de uma determinada empresa. São suportadas também pelo escalonador operações de stop de um ou todos os dispositivos de uma empresa, atualização de informações de configuração nas threads de monitoramento de dispositivos em execução no microserviço monitor, atualização de informações de um dispositivo na thread de monitoramento em execução para o dispositivo em questão, get de informações do monitor. Essas operações são explicadas em mais detalhes logo a seguir:

### 🏁 A operação de start

Esa operação permite o start de um, ou todos, os dispositivos de uma empresa. Ao tentar startar o monitoramento de um dispositivo. 

O escalonador utiliza uma variável de controle recuperada do campo "monitor_server_corrente" da tabela config no banco de dados, esse campo armazena o índice, começando de zero até o número máximo de monitor_servers registrados no banco de dados, onde, o host de cada monitor_server corresponde ao host das replicas do microserviço de monitoramento. Se há três réplicas, há três monitor_servers. O Escalonador faz o balanceamento de carga e, a cada start, esse indice é rotacionado. Se há 3 réplicas, começa de 0, depois 1, depois 2, depois o próximo é zero novamente e assim sucessivamente.

Ao tentar startar o monitoramento, o escalonador vai tentando enviar o comando/requisição de start ao monitor_server corrente, conforme o indice recuperado da tabela config. Se não conseguiu startar, é porque, ou o dispositivo já está sendo monitorado e, neste cado não há necessidade de startá-lo, ou o monitor já está no limite máximo de monitoramentos em execução configurado na tabela config do banco de dados. Ou, ainda, o monitor_server está indisponível. Se estiver indisponível ou estiver no limite, o escalonador tenta startar no próximo servidor, caso não consiga em nenhum, o microserviço principal, onde o escalonador está, retorna uma mensagem de erro informando que não foi possível startar o(s) monitoramento(s). Isto é, retorna algo como: "Foram startados 3 de 5 monitoramentos". Se o start tiver tido sucesso o escalonador atualiza no banco de dados o campo "monitor_server_corrente" para o índice do próximo monitor_server e uma resposta de sucesso é retornada ao frontend.

### 🛑 A operação de stop

A operação de stop é bem mais simples que a operação de start. Isto é, para parar um monitoramento, é necessário enviar a requisição de stop para cada monitor_server, até encontrar o monitor_server onde o monitoramento do dispositivo está em execuçao e pará-lo. Isto é, o escalonador tenta parar no primeiro monitor_server. Se não encontrar o monitoramento nele, busca e tenta parar no próximo. Se não estiver em nenhum, é retornada uma mensagem para o frontend informando que o dispositivo não está sendo monitorado, ou, se estiver tentando parar o monitoramento de todos os dispositivos da empresa, o stop do dispositivo não monitorado é ignorado. Se a execução do monitoramento em uma thread for encontrada, então, a execução é finalizada e a thread removida dos monitoramentos em execução mapeados pelo ID do dispositivo. Liberando assim, mais uma vaga para mais um monitoramento no monitor_server.

### 📑 A operação de alteração de dados da tabela config nas threads de monitoramento

Essa atualização é feita via sistema que consome o endpoint de alteração de configuração. Este, por sua vez, salva no banco de dados os dados da configuração e envia para todas as réplicas do microserviço de monitoramento (monitor_servers), através do escalonador, os dados dessa configuração para serem atualizados nas threads de monitoramento em execução em todos os monitor_servers.

### 📝 A operação de alteração de dados do dispositivo na thread de monitoramento dele

Essa atualização pode ser feita via sistema que consome o endpoint de alteração de dados do dispositivo. O endpoint, por sua vez, salva no banco de dados os dados do dispositivo e envia para todas as réplicas do microservio de monitoramento (monitor_servers), através do escalonador, os dados do dispositivo, buscando assim, em cada monitor_server, a thread de execução do monitoramento do dispositivo. Quando encontra a thread, atualiza os dados do dispositivo, setando ele no objeto da thread em execução. Essa alterção, diferente da alteração de configuração, é feita apenas em uma única execução de monitoramento, a do dispositivo cujos dados devem ser atualizados.

### 🧑‍💻 O Get de informações de um monitor_server

O acesso a informações de um monitor_server retorna, principalmente, o número de threads em execução nele. Sendo utilizado para carregar os dados dos monitor_servers do DTO de MonitorServerResponse, retornado quando são requisitados dados de um monitor_server ou dados da tabela config. O número de threads não está armazenado na tabela "monitor_server" do banco de dados, logo, deve ser buscado no monitor_server em execução.

## 🔄 A atualização de status do dispositivo via messageria RabbitMQ e WebSocket

A thread de monitoramento faz o monitoramento do dispositivo e altera o status dele conforme ele muda de ATIVO para INATIVO e vice versa. Quando o status muda, os dados do novo status são salvos no banco de dados, através do consumo da API do endpointo de update-status de dispositivo do microserviço principal. Após o status mudar, é também enviada uma mensagem com os dados do dispositivo via messageria (RabbitMQ) para o microserviço principal que, por sua vez, recebe a mensagem e a envia para o frontend via WebSocket para que o status do dispositivo seja atualizado na página de detalhes do dispositivo ou (futuramente) na página de acompanhamento de dispositivos da empresa.

O escalonador de monitores de dispositivo também envia mensagens via websocket com atualizações de dados do dispositivo quando ele muda de monitorado para não monitorado, e vice versa.

## 🕸️ A atualização dos detalhes de dispositivo via Websocket

Na página de detalhes do dispositivo são mostradas as informações do dispositivo. Inclusive se ele está sendo monitorado e seu status. Essas informações são atualizadas no backend com o monitoramento do dispositivo e mensagens enviadas via websocket são recebidas na página e, assim, as informações do dispositivo são atualizadas na tela.

Um detalhe técnico importante é o que acontece se o servidor parar de funcionar. Se isso acontecer, o websocket para de funcionar também e, então, inicia a execução periódica do teste de conexão com o servidor. Isto é, periodicamente, a cada 10 segundos, são enviadas requisições ao servidor para refresh do token de acesso. Isso porque o token pode expirar durante o período de inatividade do sistema. As requisições param de ser enviadas quando o servidor voltar a operar, o que significa que o refresh do token teve sucesso e retornou o novo token de acesso. O websocket tem em suas configurações o token de acesso atualizado com o novo token e, então, novas requisições ao servidor podem ser feitas com o novo token.

Inclusive, quando a conexão cai, o websocket fica tentando a conexão a cada 10 segundos para restabelecê-la quando o servidor voltar a funcionar e, como um novo token é gerado, caso o anterior tenha expirado no meio tempo, não há problema, pois agora será utilizado o novo token.

## 🌍 Teste de Conexão via ICMP

O sistema executa o monitoramento dos dispositivos emviando pacotes ICMP via comando ping aos dispositivos. Cada lote de pacotes enviados via ping tem uma quantidade de envios configurada no sistema. Há também um delay pequeno entre a execução do último lote e o próximo, também configurável pelo sistema.

As respostas dos comandos ping são interpretadas para identificar o número de sucessos e o número de falhas do lote de pacotes enviados. Atualmente, essa interpretação tem suporte a apenas a resposta do comando ping em português. Se o comando ping do servidor estiver em inglês, será necessário uma pequena adaptação.

Há uma configuração vinculada a empresa no sistema onde se pode configurar a porcentagem de pacotes por lote devem se perder para a conexão ser considerada falha.
