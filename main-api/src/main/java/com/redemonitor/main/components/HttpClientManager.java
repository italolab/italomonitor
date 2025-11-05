package com.redemonitor.main.components;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.redemonitor.main.dto.response.ErrorResponse;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.ErrorException;
import com.redemonitor.main.exception.Errors;

@Component
public class HttpClientManager {
	
	@Value("${microservice.access-token}") 
	private String microserviceAccessToken;
	
	public <T extends Object> T get( String uri, Class<T> clazz ) {
		RestClient client = RestClient.create();
		
		try {
			ResponseEntity<T> resp = client.get()		
				.uri( uri ) 	
				.header( "Authorization", "Bearer "+microserviceAccessToken )
				.retrieve()
				.toEntity( clazz );
			
			return resp.getBody();
		} catch ( HttpClientErrorException e ) {
			this.trataHttpClientErrorException( e, uri );
			return null;
		}	
	}
	
	public void post( String uri ) {
		RestClient client = RestClient.create();

		try {
			client.post()		
				.uri( uri ) 	
				.header( "Authorization", "Bearer "+microserviceAccessToken )
				.retrieve()
				.toBodilessEntity();								
		} catch ( HttpClientErrorException e ) {
			this.trataHttpClientErrorException( e, uri ); 
		}					
	}
	
	public <T extends Object> T postWithResponse( String uri, Class<T> clazz ) {
		RestClient client = RestClient.create();

		try {
			ResponseEntity<T> resp = client.post()		
				.uri( uri ) 	
				.header( "Authorization", "Bearer "+microserviceAccessToken )
				.retrieve()
				.toEntity( clazz );
			
			return resp.getBody();
		} catch ( HttpClientErrorException e ) {
			this.trataHttpClientErrorException( e, uri ); 
			return null;
		}					
	}
	
	public void post( String uri, Object body ) {
		RestClient client = RestClient.create();
		
		try {
			client.post()
					.uri( uri ) 	
					.header( "Authorization", "Bearer "+microserviceAccessToken )
					.contentType( MediaType.APPLICATION_JSON ) 
					.body( body )
					.retrieve()
					.toBodilessEntity();			
		} catch ( HttpClientErrorException e ) {
			this.trataHttpClientErrorException( e, uri ); 
		}			
	}
	
	public void patch( String uri, Object body ) {
		RestClient client = RestClient.create();
		
		try {
			client.patch()
				.uri( uri ) 	
				.header( "Authorization", "Bearer "+microserviceAccessToken )
				.contentType( MediaType.APPLICATION_JSON ) 
				.body( body )
				.retrieve()
				.toBodilessEntity();
		} catch ( HttpClientErrorException e ) {
			this.trataHttpClientErrorException( e, uri ); 
		}
	}
	
	private void trataHttpClientErrorException( HttpClientErrorException e, String uri ) {
		ErrorException ex = new ErrorException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
		
		Logger.getLogger( HttpClientManager.class ).warn( ex.response().getMessage()+"\nErro= "+e.getMessage() ); 

		ErrorResponse err = e.getResponseBodyAs( ErrorResponse.class );
		if ( err == null )
			throw ex;
		throw new BusinessException( err.getMessage() );
	}
	
}
