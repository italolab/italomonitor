@echo off

if %1 == upbase (
    docker compose up --build rabbitmq rede-monitor-db -d
) else if %1 == upapp (
    cd rede-monitor-main/
    .\mvnw clean package -DskipTests
    cd ..

    docker compose up --build rede-monitor-main rede-monitor-frontend -d
) else if %1 == up (
    docker compose %*
) else if %1 == down (
    docker compose %*
) else (
    echo Comando invalido.
)