package italo.italomonitor.disp_monitor.lib.to;

import java.util.Date;

public class Evento {

    private int sucessosQuant;
    private int falhasQuant;
    private int quedasQuant;
    private int tempoInatividade;
    private int duracao;
    private Date criadoEm;
    private Long dispositivoId;

	public int getSucessosQuant() {
		return sucessosQuant;
	}

	public void setSucessosQuant(int sucessosQuant) {
		this.sucessosQuant = sucessosQuant;
	}

	public int getFalhasQuant() {
		return falhasQuant;
	}

	public void setFalhasQuant(int falhasQuant) {
		this.falhasQuant = falhasQuant;
	}

	public int getQuedasQuant() {
		return quedasQuant;
	}

	public void setQuedasQuant(int quedasQuant) {
		this.quedasQuant = quedasQuant;
	}

	public int getTempoInatividade() {
		return tempoInatividade;
	}

	public void setTempoInatividade(int tempoInatividade) {
		this.tempoInatividade = tempoInatividade;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public Date getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Date criadoEm) {
		this.criadoEm = criadoEm;
	}

	public Long getDispositivoId() {
		return dispositivoId;
	}

	public void setDispositivoId(Long dispositivoId) {
		this.dispositivoId = dispositivoId;
	}
	
}
