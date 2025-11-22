package italo.italomonitor.agente;

public class Main {

	public final static String CONFIG_FILE_PATH = "config.properties";
	public final static String APPLICATION_FILE_PATH = "/application.properties";
	
	public static void main( String[] args ) {
		Sistema sistema = new Sistema();
		sistema.run( CONFIG_FILE_PATH, APPLICATION_FILE_PATH ); 					
	}
	
}
