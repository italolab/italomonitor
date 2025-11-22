package italo.italomonitor.agente.dto.integration.response;

import java.util.List;

public class Agente {

	private Long id;
	
	private String chave;
	
	private List<Long> dispositivosIDs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public List<Long> getDispositivosIDs() {
		return dispositivosIDs;
	}

	public void setDispositivosIDs(List<Long> dispositivosIDs) {
		this.dispositivosIDs = dispositivosIDs;
	}
	
}
