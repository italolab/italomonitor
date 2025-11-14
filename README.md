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
- Messageria com RabbitMQ
- React Bootstrap
- Swagger/OpenAPI
- Spring Security e Token JWT

## 🌍 Teste de Conexão via ICMP

O sistema executa o monitoramento dos dispositivos enviando pacotes ICMP via comando ping aos dispositivos. Cada lote de pacotes enviados via ping tem uma quantidade de envios configurada no sistema. Há também um delay pequeno entre a execução do último lote e o próximo, também configurável pelo sistema.

As respostas dos comandos ping são interpretadas para identificar o número de sucessos e o número de falhas do lote de pacotes enviados.

Há uma configuração vinculada a empresa no sistema onde se pode configurar a porcentagem de pacotes por lote devem se perder para a conexão ser considerada falha.
