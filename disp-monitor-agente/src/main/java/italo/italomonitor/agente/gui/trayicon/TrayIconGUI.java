package italo.italomonitor.agente.gui.trayicon;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import italo.italomonitor.agente.gui.GUIDriver;
import italo.italomonitor.agente.gui.GUIException;

public class TrayIconGUI implements TrayIconUI, ActionListener {

	private MenuItem mostrarSaidaMI;
	private MenuItem sairMI;
	
	private TrayIcon trayIcon;
	
	private TrayIconGUIListener listener;
	
	public TrayIconGUI( GUIDriver drv ) throws GUIException {
		if ( !drv.isSystemTraySupported() )
			throw new GUIException( "System Tray não suportado!" );
		
		PopupMenu popup = new PopupMenu();
		
		mostrarSaidaMI = new MenuItem( "Mostrar saída" );
		sairMI = new MenuItem( "Sair" );
		
		mostrarSaidaMI.addActionListener( this );
		sairMI.addActionListener( this ); 
		
		popup.add( mostrarSaidaMI );
		popup.addSeparator();
		popup.add( sairMI );
				
		Image icon = drv.readMainIcon();		
		
		trayIcon = new TrayIcon( icon, "Italo Monitor", popup );
		
		try {
			SystemTray.getSystemTray().add( trayIcon );
		} catch (AWTException e) {
			throw new GUIException( "Falha na adição de GUI ao System Tray." );
		}		
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		if ( listener == null )
			return;
		
		if ( e.getSource() == mostrarSaidaMI ) {
			listener.mostrarSaidaOpClick();
		} else if ( e.getSource() == sairMI ) {
			listener.sairClicadoOpClick();
		}
	}

	@Override
	public void displayInfoMessage( String message ) {
		if ( trayIcon != null )
			trayIcon.displayMessage( "Informação", message, TrayIcon.MessageType.INFO );
	}
	
	@Override
	public void displayErrorMessage( String message ) {
		if ( trayIcon != null )
			trayIcon.displayMessage( "Erro", message, TrayIcon.MessageType.ERROR );
	}

	public void setTrayIconGUIListener( TrayIconGUIListener listener ) {
		this.listener = listener;
	}
	
}
