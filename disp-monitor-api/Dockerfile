
FROM debian:12-slim AS base

RUN apt-get update -y
RUN apt-get install -y iputils-ping

FROM eclipse-temurin:latest

COPY --from=base /bin/ping /bin/ping
ADD ./target/rede-monitor-disp-monitor-api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "./app.jar"]