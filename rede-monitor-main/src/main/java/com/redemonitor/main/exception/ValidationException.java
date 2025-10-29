package com.redemonitor.main.exception;

public class ValidationException extends ErrorException {

    public ValidationException(String error, String... params) {
        super(error, params);
    }

}
