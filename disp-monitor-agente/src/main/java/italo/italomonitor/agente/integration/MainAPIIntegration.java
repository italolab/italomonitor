package italo.italomonitor.agente.integration;

import italo.italomonitor.agente.Sistema;
import italo.italomonitor.disp_monitor.lib.to.Config;

public class MainAPIIntegration {

	private final Sistema sistema;
	
	public MainAPIIntegration( Sistema sistema ) {
		this.sistema = sistema;
	}
	
	public Config getConfig() {
		String chave = sistema.getConfigProperties().getChave();
		return null;
	}
	
}
