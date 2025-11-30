package italo.italomonitor.agente.gui;

import java.awt.Image;

public interface GUIDriver {

	public Image readMainIcon() throws GUIException;
		
	public boolean isSystemTraySupported();
	
}
