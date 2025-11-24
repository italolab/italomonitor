package italo.italomonitor.agente.run;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import italo.italomonitor.agente.Sistema;
import italo.italomonitor.agente.dto.integration.response.Agente;
import italo.italomonitor.agente.exception.ErrorException;
import italo.italomonitor.disp_monitor.lib.DispositivoMonitorRunnable;
import italo.italomonitor.disp_monitor.lib.to.Config;
import italo.italomonitor.disp_monitor.lib.to.Dispositivo;

public class DispMonitoresThread extends Thread {

	private final Sistema sistema;
	private final Map<Long, DispMonitorThread> dispMonitThreads = new HashMap<>();
	
	public DispMonitoresThread(Sistema sistema) {
		this.sistema = sistema;
	}

	public void run() {
		while( !sistema.isFim() ) {
			try {
				Agente agente = sistema.getMainAPIIntegration().getAgente();
				List<Long> dispsIDs = agente.getDispositivosIDs();
								
				this.updateThreadsMap( dispsIDs );	
				
				//System.out.println( dispMonitThreads.size() + " dispositivos sendo monitorados." );
				
				this.updateConfig();
				this.updateDispositivos();
			} catch ( ErrorException e ) {
				Logger.getLogger( DispMonitoresThread.class.getName() ).severe( e.getMessage() ); 
			}
			
			try {
				long updateDelay = sistema.getConfigProperties().getUpdateDelay();
				Thread.sleep( updateDelay );
			} catch (InterruptedException e) {
				
			} 
		}
	}
	
	private void updateConfig() {
		try {		
			Config config = sistema.getMainAPIIntegration().getConfig();

			Set<Long> keys = dispMonitThreads.keySet();
			for( Long dispId : keys ) {
				DispositivoMonitorRunnable runnable = dispMonitThreads.get( dispId ).getRunnable();
				if ( runnable != null )
					runnable.setConfig( config );
			}
		} catch (ErrorException e) {
			
		}
	}
	
	private void updateDispositivos() {
		Set<Long> keys = dispMonitThreads.keySet();
		for( Long dispId : keys ) {
			try {
				Dispositivo dispositivo = sistema.getMainAPIIntegration().getDispositivo( dispId );
				DispositivoMonitorRunnable runnable = dispMonitThreads.get( dispId ).getRunnable();
				if ( runnable != null )
					runnable.setDispositivo( dispositivo );
			} catch (ErrorException e) {
				
			}
		}
	}
	
	private void updateThreadsMap( List<Long> dispIDs ) {
		for( Long dispId : dispIDs ) {			
			if ( !dispMonitThreads.containsKey( dispId ) ) {
				DispMonitorThread thread = new DispMonitorThread( sistema, dispId );								
				thread.start();
				
				dispMonitThreads.put( dispId, thread );
			}
		}
		
		Set<Long> keys = dispMonitThreads.keySet();
		for( Long keyId : keys ) {
			if ( !dispIDs.contains( keyId ) ) {
				dispMonitThreads.get( keyId ).setStop( true );
				dispMonitThreads.remove( keyId );
			}
		}
	}
	
}
