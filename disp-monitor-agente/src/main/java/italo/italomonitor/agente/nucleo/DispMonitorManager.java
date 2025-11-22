package italo.italomonitor.agente.nucleo;

import java.util.logging.Logger;

import italo.italomonitor.agente.Sistema;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.disp_monitor.lib.DispositivoMonitorListener;
import italo.italomonitor.disp_monitor.lib.DispositivoMonitorRunnable;
import italo.italomonitor.disp_monitor.lib.to.Config;
import italo.italomonitor.disp_monitor.lib.to.Dispositivo;
import italo.italomonitor.disp_monitor.lib.to.DispositivoState;
import italo.italomonitor.disp_monitor.lib.to.Evento;

public class DispMonitorManager implements DispositivoMonitorListener {

	private final Sistema sistema;
	private DispositivoMonitorRunnable dispMonitorRunnable;
	
	public DispMonitorManager( Sistema sistema ) {
		this.sistema = sistema;
	}
	
	public void initialize( Long dispositivoId ) throws ErrorException {
		Dispositivo dispositivo = sistema.getMainAPIIntegration().getDispositivo( dispositivoId );
		Config config = sistema.getMainAPIIntegration().getConfig();
		
		this.dispMonitorRunnable = new DispositivoMonitorRunnable( dispositivo, config, this );
	}
		
	@Override
	public void mensagemDispositivoStatusGerada( DispositivoState dispState ) {
		try {
			Logger.getLogger( DispMonitorManager.class.getName() ).info( "("+dispState.getId()+") - "+dispState.getStatus() ); 
			sistema.getMainAPIIntegration().postDispositivoState( dispState );
		} catch (ErrorException e) {
			
		} 
	}

	@Override
	public void mensagemEventoGerada( Evento evento ) {
		try {
			Logger.getLogger( DispMonitorManager.class.getName() ).info( "Evento gerado!" ); 
			sistema.getMainAPIIntegration().postEvento( evento );
		} catch ( ErrorException e ) {
			
		}
	}

	public DispositivoMonitorRunnable getRunnable() {
		return dispMonitorRunnable;
	}
	
}
