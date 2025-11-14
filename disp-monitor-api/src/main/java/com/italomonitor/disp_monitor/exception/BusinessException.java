package com.italomonitor.disp_monitor.exception;

public class BusinessException extends ErrorException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String error, String... params) {
        super(error, params);
    }

}
