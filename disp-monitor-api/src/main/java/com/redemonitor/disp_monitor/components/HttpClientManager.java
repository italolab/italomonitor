package com.redemonitor.disp_monitor.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.redemonitor.disp_monitor.dto.ErrorResponse;
import com.redemonitor.disp_monitor.exception.BusinessException;
import com.redemonitor.disp_monitor.exception.Errors;

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
				throw new BusinessException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
			throw new BusinessException( err.getMessage() );
		}		
	}
	
	public void post( String uri, String microserviceAccessToken ) {
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
				throw new BusinessException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
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
				throw new BusinessException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );			
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
				throw new BusinessException( Errors.ERROR_STATUS, uri, ""+e.getStatusCode().value() );
			throw new BusinessException( err.getMessage() );
		}
	}
	
}
