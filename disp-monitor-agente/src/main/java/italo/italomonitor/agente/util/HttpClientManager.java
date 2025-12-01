package italo.italomonitor.agente.util;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import italo.italomonitor.agente.Sistema;
import italo.italomonitor.agente.dto.integration.response.ErrorResponse;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.agente.exception.Errors;

public class HttpClientManager {
		
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private final Sistema sistema;
	
	public HttpClientManager( Sistema sistema ) {
		this.sistema = sistema;
	}
		
	public HttpRequest get( String uri ) {
		String accessToken = sistema.getConfigProperties().getAccessToken();
		String mainAPIHost = sistema.getConfigProperties().getMainAPIHost();
		
		return HttpRequest.newBuilder()
				.uri( URI.create( mainAPIHost + uri ) )
				.header( "Authorization", "Bearer "+accessToken )
				.GET()
				.build();
	}
	
	public HttpRequest post( String uri, Object obj ) throws ErrorException {
		String accessToken = sistema.getConfigProperties().getAccessToken();
		String mainAPIHost = sistema.getConfigProperties().getMainAPIHost();
		
		String body = this.objToString( obj );
		
		return HttpRequest.newBuilder()
				.uri( URI.create( mainAPIHost + uri ) )
				.header( "Authorization", "Bearer "+accessToken )
				.header( "Content-Type", "application/json" ) 
				.POST( HttpRequest.BodyPublishers.ofString( body ) )
				.build();
	}
	
	public void send( HttpRequest req ) throws ErrorException {
		try {
			HttpClient client = HttpClient.newHttpClient();
			client.send( req, HttpResponse.BodyHandlers.discarding() );
		} catch ( ConnectException e ) {	
			throw new ErrorException( Errors.MAINAPI_INDISPONIVEL );
		} catch ( IOException | InterruptedException e ) {
			Logger.getLogger( HttpClientManager.class.getName() ).log( Level.SEVERE, e.getMessage(), e ); 
			throw new ErrorException( e.getMessage() );
		}
	}
	
	public void asyncSend( HttpRequest req ) {
		HttpClient client = HttpClient.newHttpClient();
		client.sendAsync( req, HttpResponse.BodyHandlers.discarding() );
	}
	
	public <T extends Object> T send( HttpRequest req, Class<T> clazz ) throws ErrorException {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpResponse<String> resp = client.send( req, HttpResponse.BodyHandlers.ofString() );
			if ( resp.statusCode() == 200 )
				return objectMapper.readValue( resp.body().getBytes(), clazz );
					
			ErrorResponse errorResp = objectMapper.readValue( resp.body().getBytes(), ErrorResponse.class );
			throw new ErrorException( errorResp.getMessage() );
		} catch ( ConnectException e ) {					
			throw new ErrorException( Errors.MAINAPI_INDISPONIVEL );
		} catch ( IOException | InterruptedException e ) {
			throw new ErrorException( e.getMessage() );
		}
	}
	
	private String objToString( Object obj ) throws ErrorException {
		try {
			return objectMapper.writeValueAsString( obj );
		} catch ( JsonProcessingException e ) {
			throw new ErrorException( e.getMessage() );
		}
	}
		
}
