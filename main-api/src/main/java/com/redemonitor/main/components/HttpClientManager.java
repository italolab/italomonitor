package com.redemonitor.main.components;

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
			ErrorResponse err = e.getResponseBodyAs( ErrorResponse.class );
			if ( err == null )
				throw new ErrorException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
			throw new BusinessException( err.getMessage() );
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
			ErrorResponse err = e.getResponseBodyAs( ErrorResponse.class );
			if ( err == null )
				throw new ErrorException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
			throw new BusinessException( err.getMessage() );
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
			ErrorResponse err = e.getResponseBodyAs( ErrorResponse.class );
			if ( err == null )
				throw new ErrorException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
			throw new BusinessException( err.getMessage() );
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
			ErrorResponse err = e.getResponseBodyAs( ErrorResponse.class );
			if ( err == null )
				throw new ErrorException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
			throw new BusinessException( err.getMessage() );
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
			ErrorResponse err = e.getResponseBodyAs( ErrorResponse.class );
			if ( err == null )
				throw new ErrorException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
			throw new BusinessException( err.getMessage() );
		}
	}
	
}
