package italo.italomonitor.disp_monitor.lib.to;

import java.util.Date;

import italo.italomonitor.disp_monitor.lib.enums.DispositivoStatus;

public class DispositivoState {
		
	private Long id;
	
	private DispositivoStatus status;
	
	private int latenciaMedia;
	
	private Date ultimoStateEm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DispositivoStatus getStatus() {
		return status;
	}

	public void setStatus(DispositivoStatus status) {
		this.status = status;
	}

	public int getLatenciaMedia() {
		return latenciaMedia;
	}

	public void setLatenciaMedia(int latenciaMedia) {
		this.latenciaMedia = latenciaMedia;
	}

	public Date getUltimoStateEm() {
		return ultimoStateEm;
	}

	public void setUltimoStateEm(Date ultimoStateEm) {
		this.ultimoStateEm = ultimoStateEm;
	}
	
}
