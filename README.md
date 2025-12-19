# 🌐 Italo Monitor

O Italo Monitor é um sistema web de monitoramento de dispositivos de rede. Esse projeto é desafiador porque cada empresa registrada nele pode ter dezenas, centenas ou mais dispositivos (roteadores, servidores, etc) monitorados 24 horas por dia executando simultaneamente. O que demanda investimento em escalabilidade de microsserviços, principalmente o de monitoramento.

## 💻 Tecnologias

- Java 25
- Spring Boot 4.0.0-M3
- React 19.1.1
- Postgresql 16.3
- Docker
- Spring Scheduler com Thread Pool Task Scheduler
- Comando PING para envio de pacotes ICMP
- WebSockets
- Messageria com RabbitMQ
- React Bootstrap
- Swagger/OpenAPI
- Spring Security e Token JWT

## 🤖 Microserviços e outras partes

Abaixo, os microserviços, bibliotecas e o software agente monitor de dispositivos como pacotes do repositório:

- **disp-monitor-agente/**: Pacote onde fica o software java que monitora os dispositivos como alternativa ao monitoramento de dispositivos feito sem o agente
- **disp-monitor-lib/**: Biblioteca utilizada pelo microserviço de monitoramento e pelo software agente. Essa lib centraliza a lógica do monitoramento de dispositivos.
- **main-api/**: Onde fica a lógica principal e os endpoints do backend do sistema. É nesse microserviço onde é tratado o login e, gerado e utilizado o access token e o refresh token como cookies httponly.
- **disp-monitor-api/**: Microserviço de monitoramento de dispositivos. Esse microserviço tem réplicas escaladas com execução balanceada pelo escalonador de **main-api**.
- **frontend-app/**: Aplicação frontend onde fica a implementação em react
- **doc/**: Onde fica a documentação do sistema
- **run.bat**: Script de build e implantação das imagens docker dos microserviços do sistema.

## 🌍 Teste de Conexão via ICMP

O sistema executa o monitoramento dos dispositivos enviando pacotes ICMP via comando ping aos dispositivos. Cada lote de pacotes enviados via ping tem uma quantidade de envios configurada no sistema. Há também um delay pequeno entre a execução do último lote e o próximo, também configurável pelo sistema.

As respostas dos comandos ping são interpretadas para identificar o número de sucessos e o número de falhas do lote de pacotes enviados.

Há uma configuração vinculada a empresa no sistema onde se pode configurar a porcentagem de pacotes por lote devem se perder para a conexão ser considerada falha.
