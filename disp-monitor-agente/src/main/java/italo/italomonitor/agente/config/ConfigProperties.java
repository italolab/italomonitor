package italo.italomonitor.agente.config;

public class ConfigProperties {

	private String chave;
	
	private String accessToken;

	private final MainAPIEndpoints mainAPIEndpoints = new MainAPIEndpoints();
	
	public MainAPIEndpoints getMainAPIEndpoints() {
		return mainAPIEndpoints;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
