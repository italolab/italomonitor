package italo.italomonitor.agente.gui.image;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import italo.italomonitor.agente.gui.GUIException;
import italo.italomonitor.agente.gui.trayicon.TrayIconGUI;

public class ImageLoader {

	private Image icon;
	private Image conectadoIcon;
	private Image desconectadoIcon;
	
	public void load() throws GUIException {
		icon = this.readIcon( "/icon16x16.png" );
		conectadoIcon = this.readIcon( "/conectado.png" );
		desconectadoIcon = this.readIcon( "/desconectado.png" );
	}
	
	public Image readIcon( String iconPath ) throws GUIException {
		try {
			InputStream in = TrayIconGUI.class.getResourceAsStream( iconPath );
			if ( in == null )
				throw new GUIException( "Icone não encontrado: "+iconPath );
			return ImageIO.read( in );
		} catch ( IOException e ) {
			throw new GUIException( "Falha na leitura de: "+iconPath );
		}
	}
	
	public Image getIcon() {
		return icon;
	}
	
	public Image getConectadoIcon() {
		return conectadoIcon;
	}
	
	public Image getDesconectadoIcon() {
		return desconectadoIcon;
	}
	
}
