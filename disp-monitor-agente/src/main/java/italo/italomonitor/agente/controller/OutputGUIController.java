package italo.italomonitor.agente.controller;

import italo.italomonitor.agente.Sistema;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.agente.gui.output.OutputGUIListener;

public class OutputGUIController implements OutputGUIListener {

	private final Sistema sistema;
	
	public OutputGUIController( Sistema sistema ) {
		this.sistema = sistema;
	}
	
	@Override
	public void saindoOnClick() {
		if ( !sistema.getGUI().isSystemTraySupported() ) {
			try {
				sistema.getMainAPIIntegration().disconnectAgente();
			} catch (ErrorException e) {				
				e.printStackTrace();
			}
		}
	}

}
