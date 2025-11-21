package italo.italomonitor.agente.config;

public class MainAPIEndpoints {

	private String configGetEndpoint;
	
	private String dispositivoGetEndpoint;
	
	private String dispositivoStatePostEndpoint;
	
	private String eventoPostEndpoint;

	public String getConfigGetEndpoint() {
		return configGetEndpoint;
	}

	public void setConfigGetEndpoint(String configGetEndpoint) {
		this.configGetEndpoint = configGetEndpoint;
	}

	public String getDispositivoGetEndpoint() {
		return dispositivoGetEndpoint;
	}

	public void setDispositivoGetEndpoint(String dispositivoGetEndpoint) {
		this.dispositivoGetEndpoint = dispositivoGetEndpoint;
	}

	public String getDispositivoStatePostEndpoint() {
		return dispositivoStatePostEndpoint;
	}

	public void setDispositivoStatePostEndpoint(String dispositivoStatePostEndpoint) {
		this.dispositivoStatePostEndpoint = dispositivoStatePostEndpoint;
	}

	public String getEventoPostEndpoint() {
		return eventoPostEndpoint;
	}

	public void setEventoPostEndpoint(String eventoPostEndpoint) {
		this.eventoPostEndpoint = eventoPostEndpoint;
	}
	
}
