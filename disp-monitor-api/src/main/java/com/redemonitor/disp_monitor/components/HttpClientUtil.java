package com.redemonitor.disp_monitor.components;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.redemonitor.disp_monitor.dto.ErrorResponse;
import com.redemonitor.disp_monitor.exception.BusinessException;
import com.redemonitor.disp_monitor.exception.Errors;

@Component
public class HttpClientUtil {
	
	public <T extends Object> T get( String uri, String accessToken, Class<T> clazz ) {
		RestClient client = RestClient.create();
		
		try {
			ResponseEntity<T> resp = client.get()		
				.uri( uri ) 	
				.header( "Authorization", "Bearer "+accessToken )
				.retrieve()
				.toEntity( clazz );
			
			return resp.getBody();
		} catch ( HttpClientErrorException e ) {
			ErrorResponse err = e.getResponseBodyAs( ErrorResponse.class );
			if ( err == null )
				throw new BusinessException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
			throw new BusinessException( err.getMessage() );
		}		
	}
	
	public void post( String uri, String accessToken ) {
		RestClient client = RestClient.create();

		try {
			client.post()		
				.uri( uri ) 	
				.header( "Authorization", "Bearer "+accessToken )
				.retrieve()
				.toBodilessEntity();								
		} catch ( HttpClientErrorException e ) {
			ErrorResponse err = e.getResponseBodyAs( ErrorResponse.class );
			if ( err == null )
				throw new BusinessException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
			throw new BusinessException( err.getMessage() );
		}	
				
	}
	
	public void post( String uri, String accessToken, Object body ) {
		RestClient client = RestClient.create();
		
		try {
			client.post()
					.uri( uri ) 	
					.header( "Authorization", "Bearer "+accessToken )
					.contentType( MediaType.APPLICATION_JSON ) 
					.body( body )
					.retrieve()
					.toBodilessEntity();			
		} catch ( HttpClientErrorException e ) {
			ErrorResponse err = e.getResponseBodyAs( ErrorResponse.class );
			if ( err == null )
				throw new BusinessException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
			if ( err.getMessage() == null )
				System.out.println( "MENSAGEM NULA." );
			throw new BusinessException( err.getMessage() );
		}			
	}
	
	public void patch( String uri, String accessToken, Object body ) {
		RestClient client = RestClient.create();
		
		try {
			client.patch()
				.uri( uri ) 	
				.header( "Authorization", "Bearer "+accessToken )
				.contentType( MediaType.APPLICATION_JSON ) 
				.body( body )
				.retrieve()
				.toBodilessEntity();
		} catch ( HttpClientErrorException e ) {
			ErrorResponse err = e.getResponseBodyAs( ErrorResponse.class );
			if ( err == null )
				throw new BusinessException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
			throw new BusinessException( err.getMessage() );
		}
	}
	
}
