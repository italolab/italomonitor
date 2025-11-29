package italo.italomonitor.agente.gui;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import italo.italomonitor.agente.gui.output.OutputGUI;
import italo.italomonitor.agente.gui.output.OutputUI;
import italo.italomonitor.agente.gui.trayicon.GUIException;
import italo.italomonitor.agente.gui.trayicon.TrayIconGUI;
import italo.italomonitor.agente.gui.trayicon.TrayIconUI;

public class GUI implements GUIUtil {

	private TrayIconGUI trayIconGUI;
	private OutputGUI outputGUI;
	
	public void initialize() throws GUIException {
		this.trayIconGUI = new TrayIconGUI( this );
		this.outputGUI = new OutputGUI( this );
	}
	
	public TrayIconUI getTrayIconUI() {
		return trayIconGUI;
	}
	
	public OutputUI getOutputUI() {
		return outputGUI;
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
	
}
