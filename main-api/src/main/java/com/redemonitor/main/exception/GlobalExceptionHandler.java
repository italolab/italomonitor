package com.redemonitor.main.exception;

import org.jboss.logging.Logger;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.socket.sockjs.SockJsException;

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
    
    @ExceptionHandler(ListenerExecutionFailedException.class)
    public ResponseEntity<ErrorResponse> listenerExecutionFailedException(ListenerExecutionFailedException e ) {
    	Logger.getLogger( GlobalExceptionHandler.class ).error( e.getMessage() );
    	return ResponseEntity.internalServerError().body( new ErrorResponse( e.getMessage() ) );
    }
    
    public ResponseEntity<ErrorResponse> sockJsException( SockJsException e ) {
    	Logger.getLogger( GlobalExceptionHandler.class ).error( e.getMessage() );
    	return ResponseEntity.internalServerError().body( new ErrorResponse( e.getMessage() ) );
    }

}
