package italo.italomonitor.agente.gui;

import java.awt.SystemTray;

import italo.italomonitor.agente.gui.image.ImageLoader;
import italo.italomonitor.agente.gui.output.OutputGUI;
import italo.italomonitor.agente.gui.output.OutputUI;
import italo.italomonitor.agente.gui.trayicon.TrayIconGUI;
import italo.italomonitor.agente.gui.trayicon.TrayIconGUIListener;

public class GUI implements GUIDriver {

	private TrayIconGUI trayIconGUI = null;
	private OutputGUI outputGUI = null;
	
	private final ImageLoader imageLoader = new ImageLoader();
	
	public void initialize() throws GUIException {
		imageLoader.load();
		
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
	
	public void conectou() {
		if ( trayIconGUI != null )
			trayIconGUI.conectou();
	}
	
	public void desconectou() {
		if ( trayIconGUI != null )
			trayIconGUI.desconectou();
	}
	
	public boolean isSystemTraySupported() {
		return SystemTray.isSupported();
	}

	@Override
	public ImageLoader getImageLoader() {
		return imageLoader;
	}
	
}
