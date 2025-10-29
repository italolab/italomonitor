package com.redemonitor.main.exception;

public class BusinessException extends ErrorException {

    public BusinessException(String error, String... params) {
        super(error, params);
    }

}
