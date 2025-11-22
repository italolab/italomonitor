package italo.italomonitor.agente.nucleo.thread;

import italo.italomonitor.agente.Sistema;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.agente.nucleo.DispMonitorManager;
import italo.italomonitor.disp_monitor.lib.DispositivoMonitorRunnable;

public class DispMonitorThread extends Thread {

	private final Sistema sistema;
	private final DispMonitorManager dispMonitorManager;
	private final Long dispositivoId;
	private boolean stop = false;
	
	public DispMonitorThread( Sistema sistema, Long dispositivoId ) {
		this.sistema = sistema;
		this.dispositivoId = dispositivoId;
		this.dispMonitorManager = new DispMonitorManager( sistema );	
	}
	
	public void run() {		
		try {
			dispMonitorManager.initialize( dispositivoId );
			
			while( !stop && !sistema.isFim() )
				dispMonitorManager.getRunnable().run();
		} catch (ErrorException e) {
			
		} 					
	}
	
	public DispositivoMonitorRunnable getRunnable() {
		return dispMonitorManager.getRunnable(); 
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
		
}
