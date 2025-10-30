package com.redemonitor.main.exception;

public class ValidationException extends ErrorException {

	private static final long serialVersionUID = 1L;

	public ValidationException(String error, String... params) {
        super(error, params);
    }

}
