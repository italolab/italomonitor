package com.redemonitor.disp_monitor.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redemonitor.disp_monitor.model.Config;
import com.redemonitor.disp_monitor.util.HttpClientUtil;

@Component
public class ConfigIntegration {

	@Value("${config.get.endpoint}") 
	private String configGetEndpoint;
		
	@Autowired
	private HttpClientUtil httpClientUtil;
	
	public Config getConfig( String accessToken ) {
		String uri = configGetEndpoint;
		
		return httpClientUtil.get( uri, accessToken, Config.class );		
	}
	
}
