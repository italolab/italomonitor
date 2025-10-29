package com.redemonitor.exception;

public class BusinessException extends ErrorException {

    public BusinessException(String error, String... params) {
        super(error, params);
    }

}
