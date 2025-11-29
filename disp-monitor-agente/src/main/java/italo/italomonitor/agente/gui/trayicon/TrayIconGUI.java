package italo.italomonitor.agente.gui.trayicon;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import italo.italomonitor.agente.gui.GUIUtil;

public class TrayIconGUI implements TrayIconUI, ActionListener {

	private MenuItem mostrarSaidaMI;
	private MenuItem sairMI;
	
	private TrayIconGUIListener listener;
	
	public TrayIconGUI( GUIUtil util ) throws GUIException {
		if ( !SystemTray.isSupported() )
			throw new GUIException( "System Tray não suportado!" );
		
		PopupMenu popup = new PopupMenu();
		
		mostrarSaidaMI = new MenuItem( "Mostrar saída" );
		sairMI = new MenuItem( "Sair" );
		
		mostrarSaidaMI.addActionListener( this );
		sairMI.addActionListener( this ); 
		
		popup.add( mostrarSaidaMI );
		popup.addSeparator();
		popup.add( sairMI );
				
		Image icon = util.readMainIcon();		
		
		TrayIcon trayIcon = new TrayIcon( icon, "Italo Monitor", popup );
		
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

	public void setTrayIconGUIListener( TrayIconGUIListener listener ) {
		this.listener = listener;
	}
	
}
