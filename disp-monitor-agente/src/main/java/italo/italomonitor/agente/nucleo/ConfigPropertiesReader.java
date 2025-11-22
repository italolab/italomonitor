package italo.italomonitor.agente.nucleo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import italo.italomonitor.agente.config.ConfigProperties;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.agente.exception.Errors;

public class ConfigPropertiesReader {
	
	public ConfigProperties read( String configFilePath, String applicationFileName ) throws ErrorException {
		File file = new File( configFilePath );
		if ( !file.exists() )
			throw new ErrorException( Errors.CONFIG_FILE_NOT_FOUND, configFilePath );
		
		InputStream appFileIS = ConfigPropertiesReader.class.getResourceAsStream( applicationFileName );
		if ( appFileIS == null )
			throw new ErrorException( Errors.APPLICATION_FILE_NOT_FOUND, applicationFileName );
		
		Properties configProperties = new Properties();
		Properties applicationProperties = new Properties();
		
		try {
			configProperties.load( new FileInputStream( file ) );
		} catch ( IOException e ) {
			Logger.getLogger( ConfigPropertiesReader.class.getName() ).log( Level.SEVERE, e.getMessage(), e ); 
			throw new ErrorException( Errors.CONFIG_IO_ERROR );
		}
		
		try {
			applicationProperties.load( appFileIS );  
		} catch ( IOException e ) {
			Logger.getLogger( ConfigPropertiesReader.class.getName() ).log( Level.SEVERE, e.getMessage(), e ); 
			throw new ErrorException( Errors.FILE_IO_ERROR, applicationFileName );
		}
			
		String chave = configProperties.getProperty( "chave" );
		String updateDelay = configProperties.getProperty( "update.delay" );
		String accessToken = applicationProperties.getProperty( "access-token" ); 
		String dispositivoStatePostEndpoint = applicationProperties.getProperty( "main-api.monitoramento.agente.dispositivo-state.post" );
		String eventoPostEndpoint = applicationProperties.getProperty( "main-api.monitoramento.agente.evento.post" );
		String configGetEndpoint = applicationProperties.getProperty( "main-api.monitoramento.agente.config.get" );
		String dispositivoGetEndpoint = applicationProperties.getProperty( "main-api.monitoramento.agente.dispositivo.get" );
		String agenteGetEndpoint = applicationProperties.getProperty( "main-api.monitoramento.agente.get" );			
		
		if ( chave == null )
			throw new ErrorException( Errors.REQUIRED_PROPERTY, "chave" );
		if ( updateDelay == null )
			throw new ErrorException( Errors.REQUIRED_PROPERTY, "update.delay" );
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
		if ( agenteGetEndpoint == null )
			throw new ErrorException( Errors.PROPERTY_NOT_FOUND, "main-api.monitoramento.agente.get", applicationFileName );				
		
		ConfigProperties configProps = new ConfigProperties();						
		configProps.setChave( chave );
		configProps.setAccessToken( accessToken );
		configProps.getMainAPIEndpoints().setDispositivoStatePostEndpoint( dispositivoStatePostEndpoint );
		configProps.getMainAPIEndpoints().setEventoPostEndpoint( eventoPostEndpoint );
		configProps.getMainAPIEndpoints().setConfigGetEndpoint( configGetEndpoint );
		configProps.getMainAPIEndpoints().setDispositivoGetEndpoint( dispositivoGetEndpoint );
		configProps.getMainAPIEndpoints().setAgenteGetEndpoint( agenteGetEndpoint ); 
		
		try {
			configProps.setUpdateDelay( Long.parseLong( updateDelay ) ); 
		} catch ( NumberFormatException e ) {
			throw new ErrorException( Errors.PROPERTY_INVALID_FORMAT, "update.delay" );
		}
		
		return configProps;		
	}
	
}
