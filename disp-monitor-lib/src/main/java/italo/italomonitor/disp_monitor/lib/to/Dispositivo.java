package italo.italomonitor.disp_monitor.lib.to;

import java.util.Date;

import italo.italomonitor.disp_monitor.lib.enums.DispositivoStatus;

public class Dispositivo {

	private Long id;	
    private String host;    
    private String nome;
    private String descricao;    
    private String localizacao;    
    private boolean sendoMonitorado;    
    private DispositivoStatus status;    
    private int latenciaMedia;    
    private Date stateAtualizadoEm;    
    private boolean monitoradoPorAgente;    
    private Empresa empresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public boolean isSendoMonitorado() {
		return sendoMonitorado;
	}

	public void setSendoMonitorado(boolean sendoMonitorado) {
		this.sendoMonitorado = sendoMonitorado;
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

	public Date getStateAtualizadoEm() {
		return stateAtualizadoEm;
	}

	public void setStateAtualizadoEm(Date stateAtualizadoEm) {
		this.stateAtualizadoEm = stateAtualizadoEm;
	}

	public boolean isMonitoradoPorAgente() {
		return monitoradoPorAgente;
	}

	public void setMonitoradoPorAgente(boolean monitoradoPorAgente) {
		this.monitoradoPorAgente = monitoradoPorAgente;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
}
