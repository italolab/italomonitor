package italo.italomonitor.disp_monitor.service.device;

import italo.italomonitor.disp_monitor.lib.DispositivoMonitorListener;
import italo.italomonitor.disp_monitor.lib.DispositivoMonitorRunnable;
import italo.italomonitor.disp_monitor.lib.to.Config;
import italo.italomonitor.disp_monitor.lib.to.Dispositivo;
import italo.italomonitor.disp_monitor.lib.to.DispositivoState;
import italo.italomonitor.disp_monitor.lib.to.Evento;
import italo.italomonitor.disp_monitor.messaging.sender.DispositivoStateMessageSender;
import italo.italomonitor.disp_monitor.messaging.sender.EventoMessageSender;

public class DispositivoMonitorManager implements DispositivoMonitorListener {

	private DispositivoMonitorRunnable dispMonitorRunnable;
	private DispositivoStateMessageSender dispositivoStateMessageSender;
	private EventoMessageSender eventoMessageSender;
	
	public DispositivoMonitorManager( 
			Dispositivo dispositivo, 
			Config config,
			DispositivoStateMessageSender dispositivoStateMessageService,
            EventoMessageSender eventoMessageService ) {
		
		this.dispositivoStateMessageSender = dispositivoStateMessageService;
		this.eventoMessageSender = eventoMessageService;
		
		this.dispMonitorRunnable = new DispositivoMonitorRunnable( dispositivo, config, this );		
	}
				
	@Override
	public void mensagemDispositivoStatusGerada( DispositivoState dispState ) {
		dispositivoStateMessageSender.sendMessage( dispState );
	}

	@Override
	public void mensagemEventoGerada(Evento evento) {
		eventoMessageSender.sendMessage( evento ); 		
	}
	
	public DispositivoMonitorRunnable getRunnable() {
		return dispMonitorRunnable;
	}
	
}
