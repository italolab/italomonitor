package italo.italomonitor.agente.run;

import italo.italomonitor.agente.Sistema;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.disp_monitor.lib.DispositivoMonitorListener;
import italo.italomonitor.disp_monitor.lib.DispositivoMonitorRunnable;
import italo.italomonitor.disp_monitor.lib.to.Config;
import italo.italomonitor.disp_monitor.lib.to.Dispositivo;
import italo.italomonitor.disp_monitor.lib.to.DispositivoState;
import italo.italomonitor.disp_monitor.lib.to.Evento;

public class DispMonitorThread extends Thread implements DispositivoMonitorListener {

	private final Sistema sistema;
	private DispositivoMonitorRunnable dispMonitorRunnable;
	private final Long dispositivoId;
	private boolean stop = false;
	
	public DispMonitorThread( Sistema sistema, Long dispositivoId ) {
		this.sistema = sistema;
		this.dispositivoId = dispositivoId;
	}
	
	public void run() {		
		try {
			Dispositivo dispositivo = sistema.getMainAPIIntegration().getDispositivo( dispositivoId );
			Config config = sistema.getMainAPIIntegration().getConfig();
			
			dispMonitorRunnable = new DispositivoMonitorRunnable( dispositivo, config, this );
			
			while( !stop && !sistema.isFim() )
				dispMonitorRunnable.run();
		} catch (ErrorException e) {
			sistema.getOutputUI().printError( e.getMessage() );
		} 					
	}
	
	@Override
	public void mensagemDispositivoStatusGerada( DispositivoState dispState ) {
		try {
			sistema.getMainAPIIntegration().postDispositivoState( dispState );
		} catch (ErrorException e) {
			sistema.getOutputUI().printError( e.getMessage() );
		} 
	}

	@Override
	public void mensagemEventoGerada( Evento evento ) {
		try {
			sistema.getMainAPIIntegration().postEvento( evento );
		} catch ( ErrorException e ) {
			sistema.getOutputUI().printError( e.getMessage() );
		}
	}
	
	public DispositivoMonitorRunnable getRunnable() {
		return dispMonitorRunnable; 
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
		
}
