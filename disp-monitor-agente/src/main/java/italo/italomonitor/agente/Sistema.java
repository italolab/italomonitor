package italo.italomonitor.agente;

import java.util.logging.Logger;

import italo.italomonitor.agente.config.ConfigProperties;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.agente.integration.MainAPIIntegration;
import italo.italomonitor.agente.nucleo.ConfigPropertiesReader;
import italo.italomonitor.agente.nucleo.HttpClientManager;
import italo.italomonitor.agente.nucleo.thread.DispMonitoresThread;

public class Sistema {

	private final ConfigPropertiesReader configPropertiesReader = new ConfigPropertiesReader();
	private final MainAPIIntegration mainAPIIntegration = new MainAPIIntegration( this );
	private final HttpClientManager httpClientManager = new HttpClientManager( this );
	
	private ConfigProperties configProperties;
	private boolean fim = false;
	
	public void run( String configFilePath, String applicationFilePath ) {
		try {
			configProperties = configPropertiesReader.read( configFilePath, applicationFilePath );
			
			DispMonitoresThread thread = new DispMonitoresThread( this );
			thread.start();
			
			System.out.println( "Sistema iniciado!" ); 
		} catch ( ErrorException e ) {
			Logger.getLogger( Main.class.getName() ).severe( e.getMessage() ); 
		}	
	}

	public ConfigProperties getConfigProperties() {
		return configProperties;
	}

	public MainAPIIntegration getMainAPIIntegration() {
		return mainAPIIntegration;
	}

	public HttpClientManager getHttpClientManager() {
		return httpClientManager;
	}

	public boolean isFim() {
		return fim;
	}

	public void setFim(boolean fim) {
		this.fim = fim;
	}
	
}
