package italo.italomonitor.main.messaging.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import italo.italomonitor.main.dto.integration.DispMonitorDispositivo;
import italo.italomonitor.main.mapper.DispositivoMapper;
import italo.italomonitor.main.model.Dispositivo;

@Component
public class DispositivoMessageSender {

	@Value("${config.rabbitmq.dispositivos.exchange}")
	private String dispositivosExchange;
		
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
	
	public void sendMessage( Dispositivo dispositivo ) {
		DispMonitorDispositivo dmDispositivo = dispositivoMapper.mapToDispMonitorDispositivo( dispositivo );
		
		rabbitTemplate.convertAndSend( dispositivosExchange, "", dmDispositivo );
	}
	
}

