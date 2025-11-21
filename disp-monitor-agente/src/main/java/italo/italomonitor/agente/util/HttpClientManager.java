package italo.italomonitor.agente.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientManager {
		
	private String agenteAccessToken;
		
	public HttpResponse<String> get( String uri ) {
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri( URI.create( uri ) ) 
				.header( "Authorization", "Bearer "+agenteAccessToken )
				.GET()
				.build();
		
		try { 
			return client.send( request, HttpResponse.BodyHandlers.ofString() );			
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException( e ); 
		}		 			
	}
		
	public void getNoResponse( String uri ) {
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri( URI.create( uri ) ) 
				.header( "Authorization", "Bearer "+agenteAccessToken )
				.GET()
				.build();
		
		try { 
			client.send( request, HttpResponse.BodyHandlers.discarding() );			
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException( e ); 
		}		 								
	}
	
	public void post( String uri ) {
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri( URI.create( uri ) ) 
				.header( "Authorization", "Bearer "+agenteAccessToken )
				.POST( HttpRequest.BodyPublishers.noBody() )
				.build();
		
		try { 
			client.send( request, HttpResponse.BodyHandlers.discarding() );			
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException( e ); 
		}						
	}
		
	public HttpResponse<String> postWithResponse( String uri ) {
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri( URI.create( uri ) ) 
				.header( "Authorization", "Bearer "+agenteAccessToken )
				.POST( HttpRequest.BodyPublishers.noBody() )
				.build();
		
		try { 
			return client.send( request, HttpResponse.BodyHandlers.ofString() );			
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException( e ); 
		}				
	}
	
	public HttpResponse<String> postWithResponse( String uri, String body ) {
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri( URI.create( uri ) ) 
				.header( "Authorization", "Bearer "+agenteAccessToken )
				.POST( HttpRequest.BodyPublishers.ofString( body ) ) 
				.build();
		
		try { 
			return client.send( request, HttpResponse.BodyHandlers.ofString() );			
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException( e ); 
		}						
	}
	
	public void post( String uri, String body ) {
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri( URI.create( uri ) ) 
				.header( "Authorization", "Bearer "+agenteAccessToken )
				.POST( HttpRequest.BodyPublishers.ofString( body ) ) 
				.build();
		
		try { 
			client.send( request, HttpResponse.BodyHandlers.discarding() );			
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException( e ); 
		}			
	}
	
	public void patch( String uri, String body ) {
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri( URI.create( uri ) ) 
				.header( "Authorization", "Bearer "+agenteAccessToken )
				.method( "PATCH", HttpRequest.BodyPublishers.ofString( body ) ) 
				.build();
		
		try { 
			client.send( request, HttpResponse.BodyHandlers.discarding() );			
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException( e ); 
		}
	}
		
}
