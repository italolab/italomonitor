package com.redemonitor.exception;

public interface Errors {

    public final static String NOT_AUTHORIZED = "Acesso não autorizado.";

    public final static String USER_NOT_FOUND = "Usuário não encontrado.";
    public final static String USER_ALREADY_EXISTS = "Usuário já existe registrado com username informado.";

    public final static String REQUIRED_FIELD = "O campo '$1' é de preenchimento obrigatório.";
    public final static String NOT_JOKER_AND_NOT_INT = "O campo '$1' não é numérico nem asterisco.";
    public final static String VALUE_LESS_OR_EQUALS_THAN_ZERO = "O valor do campo '$1' é menor ou igual a zero ou não numérico.";
    public final static String INVALID_EMAIL = "O valor do campo '$1' está em formato inválido.";


}
