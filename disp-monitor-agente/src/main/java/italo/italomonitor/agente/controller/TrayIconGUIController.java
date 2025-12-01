package italo.italomonitor.agente.controller;

import italo.italomonitor.agente.Sistema;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.agente.gui.trayicon.TrayIconGUIListener;

public class TrayIconGUIController implements TrayIconGUIListener {

	private final Sistema sistema;
	
	public TrayIconGUIController( Sistema sistema ) {
		this.sistema = sistema;
	}
	
	@Override
	public void mostrarSaidaOpClick() {
		sistema.getGUI().getOutputUI().setVisible( true ); 
	}

	@Override
	public void sairClicadoOpClick() {
		sistema.setFim( true );
		try {
			sistema.getMainAPIIntegration().disconnectAgente();
		} catch (ErrorException e) {				
			e.printStackTrace();
		}
		System.exit( 0 ); 
	}

}
