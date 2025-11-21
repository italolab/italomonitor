package italo.italomonitor.agente;

import java.util.logging.Logger;

import italo.italomonitor.agente.exception.ErrorException;

public class Main {

	public final static String CONFIG_FILE_PATH = "config.properties";
	public final static String APPLICATION_FILE_PATH = "application.properties";
	
	public static void main( String[] args ) {
		try {
			Sistema sistema = new Sistema();
			sistema.inicializa( CONFIG_FILE_PATH, APPLICATION_FILE_PATH ); 			
		} catch ( ErrorException e ) {
			Logger.getLogger( Main.class.getName() ).severe( e.getMessage() ); 
		}
	}
	
}
