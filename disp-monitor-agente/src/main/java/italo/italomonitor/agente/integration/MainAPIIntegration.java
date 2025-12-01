package italo.italomonitor.agente.integration;

import java.net.http.HttpRequest;

import italo.italomonitor.agente.Sistema;
import italo.italomonitor.agente.dto.integration.response.Agente;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.agente.util.HttpClientManager;
import italo.italomonitor.disp_monitor.lib.to.Config;
import italo.italomonitor.disp_monitor.lib.to.Dispositivo;
import italo.italomonitor.disp_monitor.lib.to.DispositivoState;
import italo.italomonitor.disp_monitor.lib.to.Evento;

public class MainAPIIntegration {

	private final Sistema sistema;
	
	public MainAPIIntegration( Sistema sistema ) {
		this.sistema = sistema;
	}
	
	public void disconnectAgente() throws ErrorException {
		String chave = sistema.getConfigProperties().getChave();
		
		String uri = sistema.getConfigProperties().getMainAPIEndpoints().getDisconnectAgenteEndpoint();
		uri = uri.replace( "{chave}", chave );
		
		HttpClientManager httpClientManager = sistema.getHttpClientManager();
		
		HttpRequest req = httpClientManager.post( uri );
		httpClientManager.asyncSend( req ); 
	}
	
	public void postDispositivoState( DispositivoState dispState ) throws ErrorException {
		String chave = sistema.getConfigProperties().getChave();
		
		String uri = sistema.getConfigProperties().getMainAPIEndpoints().getDispositivoStatePostEndpoint();		
		uri = uri.replace( "{chave}", chave );
				
		HttpClientManager httpClientManager = sistema.getHttpClientManager();
		
		HttpRequest req = httpClientManager.post( uri, dispState );		
		httpClientManager.asyncSend( req );
	}
	
	public void postEvento( Evento evento ) throws ErrorException {
		String chave = sistema.getConfigProperties().getChave();
		
		String uri = sistema.getConfigProperties().getMainAPIEndpoints().getEventoPostEndpoint();		
		uri = uri.replace( "{chave}", chave );
				
		HttpClientManager httpClientManager = sistema.getHttpClientManager();
		
		HttpRequest req = httpClientManager.post( uri, evento );		
		httpClientManager.asyncSend( req );
	}
	
	public Agente getAgente() throws ErrorException {
		String chave = sistema.getConfigProperties().getChave();
		
		String uri = sistema.getConfigProperties().getMainAPIEndpoints().getAgenteGetEndpoint();		
		uri = uri.replace( "{chave}", chave );
				
		HttpClientManager httpClientManager = sistema.getHttpClientManager();
		
		HttpRequest req = httpClientManager.get( uri );		
		return httpClientManager.send( req, Agente.class );
	}
	
	public Config getConfig() throws ErrorException {
		String chave = sistema.getConfigProperties().getChave();
		
		String uri = sistema.getConfigProperties().getMainAPIEndpoints().getConfigGetEndpoint();		
		uri = uri.replace( "{chave}", chave );
				
		HttpClientManager httpClientManager = sistema.getHttpClientManager();
		
		HttpRequest req = httpClientManager.get( uri );		
		return httpClientManager.send( req, Config.class );
	}
	
	public Dispositivo getDispositivo( Long dispositivoId ) throws ErrorException {
		String chave = sistema.getConfigProperties().getChave();
		
		String uri = sistema.getConfigProperties().getMainAPIEndpoints().getDispositivoGetEndpoint();		
		uri = uri.replace( "{chave}", chave ).replace( "{dispositivoId}", ""+dispositivoId );
				
		HttpClientManager httpClientManager = sistema.getHttpClientManager();

		HttpRequest req = httpClientManager.get( uri );		
		return httpClientManager.send( req, Dispositivo.class );
	}
	
}
