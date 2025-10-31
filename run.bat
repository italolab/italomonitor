@echo off

if %1 == upall (
    call :upbase
    call :upmain
    call :updm
    call :upfront
) else if %1 == upapp (
    call :upmain
    call :updm
    call :upfront
) else if %1 == upbase (
    call :upbase
) else if %1 == upmain (
    call :upmain
) else if %1 == updm (
    call :updm
) else if %1 == upfront (
    call :upfront
) else if %1 == up (
    docker compose %*
) else if %1 == down (
    docker compose %*
) else (
    echo Comando invalido.
)

goto :fim

:upmain
    cd rede-monitor-main/
    call .\mvnw.cmd clean package -DskipTests
    cd ..

    docker compose up --build rede-monitor-main -d
exit /b 0

:updm
    cd rede-monitor-disp-monitor/
    call .\mvnw.cmd clean package -DskipTests
    cd ..

    docker compose up --build rede-monitor-disp-monitor -d
exit /b 0

:upfront
    docker compose up --build rede-monitor-frontend -d
exit /b 0

:upbase
    docker compose up --build rabbitmq rede-monitor-db -d
exit /b 0


:fim