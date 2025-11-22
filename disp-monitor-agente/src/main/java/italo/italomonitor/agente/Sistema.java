package italo.italomonitor.agente;

import italo.italomonitor.agente.config.ConfigProperties;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.agente.integration.MainAPIIntegration;
import italo.italomonitor.agente.nucleo.ConfigPropertiesReader;
import italo.italomonitor.agente.nucleo.thread.DispMonitoresThread;
import italo.italomonitor.agente.nucleo.util.HttpClientManager;

public class Sistema {

	private final ConfigPropertiesReader configPropertiesReader = new ConfigPropertiesReader();
	private final MainAPIIntegration mainAPIIntegration = new MainAPIIntegration( this );
	private final HttpClientManager httpClientManager = new HttpClientManager( this );
	
	private ConfigProperties configProperties;
	private boolean fim = false;
	
	public void run( String configFilePath, String applicationFilePath ) throws ErrorException {
		configProperties = configPropertiesReader.read( configFilePath, applicationFilePath );
		
		DispMonitoresThread thread = new DispMonitoresThread( this );
		thread.start();
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
