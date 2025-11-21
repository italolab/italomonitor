package italo.italomonitor.agente.nucleo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import italo.italomonitor.agente.config.ConfigProperties;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.agente.exception.Errors;

public class ConfigPropertiesReader {
	
	public ConfigProperties read( String configFilePath, String applicationFileName ) throws ErrorException {
		File file = new File( configFilePath );
		if ( !file.exists() )
			throw new ErrorException( Errors.CONFIG_FILE_NOT_FOUND, configFilePath );
		
		Properties configProperties = new Properties();
		Properties applicationProperties = new Properties();
		
		try {
			configProperties.load( new FileInputStream( configFilePath ) );
		} catch ( IOException e ) {
			e.printStackTrace();
			throw new ErrorException( Errors.CONFIG_IO_ERROR );
		}
		
		try {
			applicationProperties.load( ConfigPropertiesReader.class.getResourceAsStream( applicationFileName ) );  
		} catch ( IOException e ) {
			e.printStackTrace();
			throw new ErrorException( Errors.APPLICATION_FILE_NOT_FOUND );
		}
			
		String chave = configProperties.getProperty( "chave" );
		String accessToken = applicationProperties.getProperty( "access-token" ); 
		String dispositivoStatePostEndpoint = applicationProperties.getProperty( "main-api.monitoramento.agente.dispositivo-state.post" );
		String eventoPostEndpoint = applicationProperties.getProperty( "main-api.monitoramento.agente.evento.post" );
		String configGetEndpoint = applicationProperties.getProperty( "main-api.monitoramento.agente.config.get" );
		String dispositivoGetEndpoint = applicationProperties.getProperty( "main-api.monitoramento.agente.dispositivo.get" );
					
		if ( chave == null )
			throw new ErrorException( Errors.REQUIRED_PROPERTY, "chave" );
		if ( accessToken == null )
			throw new ErrorException( Errors.PROPERTY_NOT_FOUND, "access-token", applicationFileName );
		if ( dispositivoStatePostEndpoint == null )
			throw new ErrorException( Errors.PROPERTY_NOT_FOUND, "main-api.monitoramento.agente.dispositivo-state.post", applicationFileName );
		if ( eventoPostEndpoint == null )
			throw new ErrorException( Errors.PROPERTY_NOT_FOUND, "main-api.monitoramento.agente.evento.post", applicationFileName );
		if ( configGetEndpoint == null )
			throw new ErrorException( Errors.PROPERTY_NOT_FOUND, "main-api.monitoramento.agente.config.get", applicationFileName );
		if ( dispositivoGetEndpoint == null )
			throw new ErrorException( Errors.PROPERTY_NOT_FOUND, "main-api.monitoramento.agente.dispositivo.get", applicationFileName );
		
		ConfigProperties configProps = new ConfigProperties();						
		configProps.setChave( chave );
		configProps.setAccessToken( accessToken );
		configProps.getMainAPIEndpoints().setDispositivoStatePostEndpoint( dispositivoStatePostEndpoint );
		configProps.getMainAPIEndpoints().setEventoPostEndpoint( eventoPostEndpoint );
		configProps.getMainAPIEndpoints().setConfigGetEndpoint( configGetEndpoint );
		configProps.getMainAPIEndpoints().setDispositivoGetEndpoint( dispositivoGetEndpoint ); 
		return configProps;		
	}
	
}
