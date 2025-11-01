package com.redemonitor.main.mapper;

import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.request.SaveMonitorServerRequest;
import com.redemonitor.main.dto.response.MonitorServerResponse;
import com.redemonitor.main.model.MonitorServer;

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
	
}
