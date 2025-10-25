# 🌐 Rede Monitor

O Rede Monitor é um sistema web de monitoramento de dispositivos de rede. Esse 
projeto é desafiador porque cada empresa registrada nele pode ter dezenas, centenas 
ou mais dispositivos (roteadores, servidores, etc) monitorados 24 horas por dia 
executando simultaneamente. O que demanda investimento em escalabilidade de 
microsserviços, principalmente o de monitoramento.

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

## 🔓 Segurança e Autenticação

A segurança no backend envolve Spring Security e Token JWT.

### 🔒 Os Tokens

O Access token é armazenado em um cookie httponly e tem tempo de expiração de 5 minutos e seu payload 
contém as seguintes claims adicionais:

- <ins>**username**</ins>: como subject
- <ins>**roles**</ins>: papeis do usuário embutidos no token como array de strings

O refresh token é armazenado em um cookie httponly e tem tempo de expiração de 30 minutos e seu payload 
contem as seguintes claims adicionais:

- <ins>**username**</ins>: como subject

### 🛡️ O Filtro de Authorização

O filtro de autorização funciona da seguinte forma: Se for feita a requisição para o endpoint de 
login, então o cookie que é responsável por armazenar o token de acesso é removido e o fluxo segue 
para o controller e service de login. Caso contrário, o filtro de autorização extrai o token do 
cookie, valida, extrai o username como subject e os roles embutidos e carrega o objeto Authentication 
com username e os roles para a autorização do spring security funcionar. Caso o token seja inválido ou 
esteja expirado, o backend retorna uma mensagem de erro.

### 🔑 O Login

Durante a autenticação, acontece o seguinte: O frontend envia uma requisição para o endpoint de login. O 
service do endpoint de login busca o usuário, seus grupos e roles no banco de dados pelo username e senha e 
gera o access token e o refresh token. Então, gera os cookies httponly para o access token e o refresh token 
e os retorna para o navegador armazenar os cookies como httponly e os reenviar a cada nova requisição aos 
endpoints do backend.

### 🌍 O interceptor de API no frontend e o Refresh Token

Quando o frontend envia uma requisição e o filtro de autorização retorna uma mensagem 401 ou 403, o 
interceptor de API captura a resposta e supõe que o token expirou. Então, gera uma nova requisição para 
o endpoint de refresh token para que um novo token seja gerado. Por sua vez, o service utilizado pelo 
endpoint de refresh token faz o seguinte:

- extrai o refresh token do cookie
- extrai o username do refresh token
- Busca o usuário pelo username
- Carrega os roles do usuário
- Gera o novo token de acesso
- Gera um novo cookie httponly com o token de acesso e retorna para o navegador
- Retorna um DTO de LoginResponse com o novo token, o username e o nome do usuário

Após isto, o interceptor de API recebe o LoginResponse, acessa o access token e seta o access token 
no AuthContext de Context API.

O access token armazenado no estado da aplicação via Context API é utilizado pelo websocket de atualização 
de detalhes do dispositivo.

### 📡 A autenticação e autorização via Websocket

O websocket tem um interceptor configurado, onde, o token de acesso deve ser extraído do cabeçalho de 
token de acesso bearer. Então, assim como no filtro de autorização, o interceptor de websocket valida o 
token e extrai os roles do token. Após isso, o interceptor de websocket verifica se o role 
"dispositivo-read" está presente nos roles estraídos e, caso sim, carrega o username extraído do token 
para o envio de mensagens para o usuário específico pelo username funcionar. Isso garante que apenas os 
usuários com o token válido e com o role necessário e o devido username recebam a mensagem enviada.

### 👩‍💻 Configuração de Cors

A configuração de cors está no backend da seguinte forma:

- A origem é o host com porta do local onde o frontend estiver hospedado ou localhost:5173 para 
desenvolvimento.
- O mapping path tem o seguinte padrão: /** englobando todos os endpoints da API e do websocket
- Todos os cabeçalhos são permitidos
- Todos os métodos são permitidos
- As credenciais são permitidas, o que necessita do '*withCredentials: true*' nas requisições via axios

## 🌍 Teste de Conexão via ICMP

O sistema executa o monitoramento dos dispositivos emviando pacotes ICMP via comando ping aos dispositivos. 
Cada lote de pacotes enviados via ping tem uma quantidade de envios configurada no sistema. Há também um 
delay pequeno entre a execução do último lote e o próximo, também configurável pelo sistema.

As respostas dos comandos ping são interpretadas para identificar o número de sucessos e o número de falhas 
do lote de pacotes enviados. Atualmente, essa interpretação tem suporte a apenas a resposta do comando ping 
em português. Se o comando ping do servidor estiver em inglês, será necessário uma pequena adaptação.
