package com.redemonitor.exception;

import com.redemonitor.controller.dto.response.ErrorResponse;

public class ErrorException extends RuntimeException {

    private final String error;
    private final String[] params;

    public ErrorException( String error, String... params ) {
        this.error = error;
        this.params = params;
    }

    public ErrorResponse response() {
        String msg = error;
        for( int i = 0; i < params.length; i++ )
            msg = msg.replace( "$"+(i+1), params[ i ] );

        return ErrorResponse.builder()
                .message( msg )
                .build();
    }

}
