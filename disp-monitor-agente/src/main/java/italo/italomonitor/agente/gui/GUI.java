package italo.italomonitor.agente.gui;

import java.awt.Image;
import java.awt.SystemTray;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import italo.italomonitor.agente.gui.output.OutputGUI;
import italo.italomonitor.agente.gui.output.OutputUI;
import italo.italomonitor.agente.gui.trayicon.TrayIconGUI;
import italo.italomonitor.agente.gui.trayicon.TrayIconGUIListener;

public class GUI implements GUIDriver {

	private TrayIconGUI trayIconGUI = null;
	private OutputGUI outputGUI = null;
	
	public void initialize() throws GUIException {
		if ( this.isSystemTraySupported() )
			this.trayIconGUI = new TrayIconGUI( this );		
		
		this.outputGUI = new OutputGUI( this );
	}
	
	public void setTrayIconGUIListener( TrayIconGUIListener listener ) {
		if ( trayIconGUI != null )
			trayIconGUI.setTrayIconGUIListener( listener ); 
	}
		
	public OutputUI getOutputUI() {
		return outputGUI;
	}
	
	public void printError( String text ) {
		this.printError( text, true ); 
	}
	
	public void printInfo( String text ) {
		this.printInfo( text, true ); 
	}
	
	public void printError( String text, boolean mostrarEmSystemTray ) {
		outputGUI.printError( text );
		if ( trayIconGUI != null && mostrarEmSystemTray )
			trayIconGUI.displayErrorMessage( text ); 
	}
	
	public void printInfo( String text, boolean mostrarEmSystemTray ) {
		outputGUI.printInfo( text );
		if ( trayIconGUI != null && mostrarEmSystemTray )
			trayIconGUI.displayInfoMessage( text ); 
	}

	@Override
	public Image readMainIcon() throws GUIException {
		try {
			InputStream in = TrayIconGUI.class.getResourceAsStream( "/icon16x16.png" );
			if ( in == null )
				throw new GUIException( "Icone de trayicon não encontrado: /icon16x16.png" );
			return ImageIO.read( in );
		} catch ( IOException e ) {
			throw new GUIException( "Falha na leitura de: /icon16x16.png" );
		}
	}
	
	public boolean isSystemTraySupported() {
		return SystemTray.isSupported();
	}
	
}
