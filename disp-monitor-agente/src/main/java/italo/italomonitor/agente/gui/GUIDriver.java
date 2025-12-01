package italo.italomonitor.agente.gui;

import italo.italomonitor.agente.gui.image.ImageLoader;

public interface GUIDriver {

	public ImageLoader getImageLoader();
		
	public boolean isSystemTraySupported();
	
}
