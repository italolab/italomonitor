package com.redemonitor.exception;

import com.redemonitor.controller.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


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
