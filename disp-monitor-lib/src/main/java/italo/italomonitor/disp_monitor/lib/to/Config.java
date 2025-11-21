package italo.italomonitor.disp_monitor.lib.to;

public class Config {

	private int monitoramentoDelay;
    private int numPacotesPorLote;
    private int registroEventoPeriodo;    
    private int numThreadsLimite;
	
    public int getMonitoramentoDelay() {
		return monitoramentoDelay;
	}
	
	public void setMonitoramentoDelay(int monitoramentoDelay) {
		this.monitoramentoDelay = monitoramentoDelay;
	}
	
	public int getNumPacotesPorLote() {
		return numPacotesPorLote;
	}
	
	public void setNumPacotesPorLote(int numPacotesPorLote) {
		this.numPacotesPorLote = numPacotesPorLote;
	}
	
	public int getRegistroEventoPeriodo() {
		return registroEventoPeriodo;
	}
	
	public void setRegistroEventoPeriodo(int registroEventoPeriodo) {
		this.registroEventoPeriodo = registroEventoPeriodo;
	}
	
	public int getNumThreadsLimite() {
		return numThreadsLimite;
	}
	
	public void setNumThreadsLimite(int numThreadsLimite) {
		this.numThreadsLimite = numThreadsLimite;
	}
    
}
