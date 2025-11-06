package com.redemonitor.disp_monitor.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redemonitor.disp_monitor.components.HttpClientManager;
import com.redemonitor.disp_monitor.dto.response.integration.ConfigResponse;
import com.redemonitor.disp_monitor.mapper.ConfigMapper;
import com.redemonitor.disp_monitor.model.Config;

@Component
public class ConfigIntegration {

	@Value("${config.get.endpoint}") 
	private String configGetEndpoint;
		
	@Autowired
	private ConfigMapper configMapper;
	
	@Autowired
	private HttpClientManager httpClientManager;
	
	public Config getConfig() {
		String uri = configGetEndpoint;
		
		ConfigResponse resp = httpClientManager.get( uri, ConfigResponse.class );
		
		return configMapper.map( resp ); 
	}
	
}
