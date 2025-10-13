package com.redemonitor.exception;

public class ValidationException extends ErrorException {

    public ValidationException(String error, String... params) {
        super(error, params);
    }

}
