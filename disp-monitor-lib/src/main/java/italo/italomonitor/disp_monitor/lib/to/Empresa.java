package italo.italomonitor.disp_monitor.lib.to;

public class Empresa {

	private Long id;
        
    private double porcentagemMaxFalhasPorLote;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPorcentagemMaxFalhasPorLote() {
		return porcentagemMaxFalhasPorLote;
	}

	public void setPorcentagemMaxFalhasPorLote(double porcentagemMaxFalhasPorLote) {
		this.porcentagemMaxFalhasPorLote = porcentagemMaxFalhasPorLote;
	}
	
}
