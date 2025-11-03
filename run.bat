@echo off

if %1 == upall (
    call :upbase
    call :upmain
    call :updispmnt
    call :upfront
) else if %1 == upapp (
    call :upmain
    call :updispmnt
    call :upfront
) else if %1 == upmicros (
    call :upmain
    call :updispmnt
) else if %1 == upbase (
    call :upbase
) else if %1 == upmain (
    call :upmain
) else if %1 == updispmnt (
    call :updispmnt
) else if %1 == upfront (
    call :upfront
) else if %1 == downall (
    call :downbase
    call :downmain
    call :downdispmnt
    call :downfront
) else if %1 == downapp (
    call :downmain
    call :downdispmnt
    call :downfront
) else if %1 == downmicros (
    call :downmain
    call :downdispmnt
) else if %1 == downbase (
    call :downbase
) else if %1 == downmain (
    call :downmain
) else if %1 == downdispmnt (
    call :downdispmnt
) else if %1 == downfront (
    call :downfront
) else if %1 == up (
    docker compose %*
) else if %1 == down (
    docker compose %*
) else (
    echo Comando invalido.
)

goto :fim

:upmain
    cd main-api/
    call .\mvnw.cmd clean package -DskipTests
    cd ..

    docker compose up --build main-api -d
exit /b 0

:updispmnt
    cd disp-monitor-api/
    call .\mvnw.cmd clean package -DskipTests
    cd ..

    docker compose up --build disp-monitor-api-1 disp-monitor-api-2 disp-monitor-api-3 -d
exit /b 0

:upfront
    docker compose up --build frontend-app -d
exit /b 0

:upbase
    docker compose up --build rabbitmq-server main-db-server -d
exit /b 0

:downmain
    docker compose down main-api
exit /b 0

:downdispmnt
    docker compose down disp-monitor-api-1 disp-monitor-api-2 disp-monitor-api-3
exit /b 0

:downfront
    docker compose down frontend-app
exit /b 0

:downbase
    docker compose down rabbitmq-server main-db-server
exit /b 0

:fim