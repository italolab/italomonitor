package italo.italomonitor.agente;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import italo.italomonitor.agente.config.ConfigProperties;
import italo.italomonitor.agente.controller.TrayIconGUIController;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.agente.gui.GUI;
import italo.italomonitor.agente.gui.GUIException;
import italo.italomonitor.agente.gui.output.OutputUI;
import italo.italomonitor.agente.integration.MainAPIIntegration;
import italo.italomonitor.agente.run.DispMonitoresThread;
import italo.italomonitor.agente.util.ConfigPropertiesReader;
import italo.italomonitor.agente.util.HttpClientManager;

public class Sistema {

	private final ConfigPropertiesReader configPropertiesReader = new ConfigPropertiesReader();
	private final MainAPIIntegration mainAPIIntegration = new MainAPIIntegration( this );
	private final HttpClientManager httpClientManager = new HttpClientManager( this );
	
	private final TrayIconGUIController trayIconGUIController = new TrayIconGUIController( this );
	private GUI gui;
	
	private ConfigProperties configProperties;
	private boolean fim = false;
	
	public void run( String configFilePath, String applicationFilePath ) {	
		SwingUtilities.invokeLater( () -> {
			gui = new GUI();
			try {
				gui.initialize();
				gui.setTrayIconGUIListener( trayIconGUIController );
									
				configProperties = configPropertiesReader.read( configFilePath, applicationFilePath );
				
				DispMonitoresThread thread = new DispMonitoresThread( this );
				thread.start();

				if ( !gui.isSystemTraySupported() )
					gui.getOutputUI().setVisible( true );
				
				gui.printInfo( "Sistema iniciado!" );
			} catch ( GUIException e ) {
				JOptionPane.showMessageDialog( null, e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE );			
			} catch ( ErrorException e ) {
				gui.printError( e.getMessage() ); 				
			}
		} );											
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

	public GUI getGUI() {
		return gui;
	}
	
	public OutputUI getOutputUI() {
		return gui.getOutputUI();
	}
	
}
