package com.redemonitor.exception;

public interface Errors {

    public final static String NOT_AUTHORIZED = "Acesso não autorizado.";
    public final static String INVALID_OR_EXPIRED_TOKEN = "Token inválido ou expirado. Por favor faça login novamente.";

    public final static String USER_NOT_FOUND = "Usuário não encontrado.";
    public final static String USER_ALREADY_EXISTS = "Usuário já existe registrado com username informado.";

    public final static String USER_GROUP_NOT_FOUND = "Grupo de usuário não encontrado.";
    public final static String USER_GROUP_ALREADY_EXISTS = "Grupo de usuário já existe registrado com o nome informado.";

    public final static String ROLE_NOT_FOUND = "Role não encontrado.";
    public final static String ROLE_ALREADY_EXISTS = "Role já existe registrado com o nome informado.";

    public final static String EMPRESA_NOT_FOUND = "Empresa não encontrada.";
    public final static String EMPRESA_ALREADY_EXISTS = "Empresa já existe registrada com o nome informado.";

    public final static String DISPOSITIVO_NOT_FOUND = "Dispositivo não encontrado.";
    public final static String DISPOSITIVO_ALREADY_EXISTS = "Dispositivo já existe registrado com o nome informado.";
    public final static String DISPOSITIVO_ALREADY_MONITORED = "O dispositivo já está sendo monitorado.";
    public final static String DISPOSITIVO_NOT_MONITORED = "O dispositivo não está sendo monitorado.";

    public final static String EVENT_NOT_FOUND = "Evento não encontrado.";

    public final static String LINK_USER_GROUP_NOT_FOUND = "Vínculo entre usuário e grupo não encontrado.";
    public final static String LINK_ROLE_USERGROUP_NOT_FOUND = "Vínculo entre grupo de usuário e role não encontrado.";

    public final static String REQUIRED_FIELD = "O campo '$1' é de preenchimento obrigatório.";
    public final static String NOT_JOKER_AND_NOT_INT = "O campo '$1' não é numérico nem asterisco.";
    public final static String VALUE_LESS_OR_EQUALS_THAN_ZERO = "O valor do campo '$1' é menor ou igual a zero ou não numérico.";
    public final static String INVALID_EMAIL = "O valor do campo '$1' está em formato inválido.";


}
