package com.italomonitor.main.mapper;

import org.springframework.stereotype.Component;

import com.italomonitor.main.components.DispositivoMonitorEscalonador.MonitorInfo;
import com.italomonitor.main.dto.request.SaveMonitorServerRequest;
import com.italomonitor.main.dto.response.MonitorServerResponse;
import com.italomonitor.main.model.MonitorServer;

@Component
public class MonitorServerMapper {

	public MonitorServer map( SaveMonitorServerRequest request ) {
		return MonitorServer.builder()
				.host( request.getHost() )
				.build();
	}
	
	public MonitorServerResponse map( MonitorServer monitorServer ) {
		return MonitorServerResponse.builder()
				.id( monitorServer.getId() )
				.host( monitorServer.getHost() ) 
				.build();
	}
	
	public void load( MonitorServer monitorServer, SaveMonitorServerRequest request ) {
		monitorServer.setHost( request.getHost() ); 
	}
	
	public void load( MonitorServerResponse resp, MonitorInfo info ) {
		resp.setInfo( info.getInfo() ); 
		resp.setAtivo( info.isAtivo() );
	}
	
}
