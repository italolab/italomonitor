package italo.italomonitor.agente;

import italo.italomonitor.agente.config.ConfigProperties;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.agente.integration.MainAPIIntegration;
import italo.italomonitor.agente.nucleo.ConfigPropertiesReader;

public class Sistema {

	private final ConfigPropertiesReader configPropertiesReader = new ConfigPropertiesReader();
	private final MainAPIIntegration mainAPIIntegration = new MainAPIIntegration( this );
	
	private ConfigProperties configProperties;
	
	public void inicializa( String configFilePath, String applicationFilePath ) throws ErrorException {
		configProperties = configPropertiesReader.read( configFilePath, applicationFilePath );
	}

	public ConfigProperties getConfigProperties() {
		return configProperties;
	}

	public MainAPIIntegration getMainAPIIntegration() {
		return mainAPIIntegration;
	}
	
}
