package com.redemonitor.disp_monitor.exception;

public interface Errors {

    public final static String NOT_AUTHORIZED = "Acesso não autorizado.";
    public final static String INVALID_OR_EXPIRED_TOKEN = "Disp Monitor - Token inválido ou expirado. Por favor faça login novamente.";

    public final static String DISPOSITIVO_NOT_FOUND = "Dispositivo não encontrado.";
    public final static String DISPOSITIVO_ALREADY_EXISTS = "Dispositivo já existe registrado com o nome informado.";
    public final static String DISPOSITIVO_ALREADY_MONITORED = "O dispositivo já está sendo monitorado.";
    public final static String DISPOSITIVO_NOT_MONITORED = "O dispositivo não está sendo monitorado.";

    public final static String EVENT_NOT_FOUND = "Evento não encontrado.";

    public final static String ERROR_STATUS = "Erro no microserviço de monitoramento de dispositivos.\nURI=$1\nStatus=$2";
    
}
