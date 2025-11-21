package italo.italomonitor.disp_monitor.lib;

import italo.italomonitor.disp_monitor.lib.to.DispositivoState;
import italo.italomonitor.disp_monitor.lib.to.Evento;

public interface DispositivoMonitorListener {

	public void mensagemDispositivoStatusGerada( DispositivoState dispState );
	
	public void mensagemEventoGerada( Evento evento );
	
}
