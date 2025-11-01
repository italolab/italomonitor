package com.redemonitor.main.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.redemonitor.main.dto.response.ErrorResponse;


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
