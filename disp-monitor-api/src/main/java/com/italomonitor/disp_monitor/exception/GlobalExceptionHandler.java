package com.italomonitor.disp_monitor.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.italomonitor.disp_monitor.dto.response.ErrorResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorResponse> errorException(ErrorException e ) {
        return ResponseEntity.badRequest().body( e.response() );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException e ) {
        return ResponseEntity.status( 403 ).body( new ErrorResponse( Errors.NOT_AUTHORIZED ) );
    }

}
