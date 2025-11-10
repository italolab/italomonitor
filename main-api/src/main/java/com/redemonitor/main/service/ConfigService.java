package com.redemonitor.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redemonitor.main.components.DispositivoMonitorEscalonador;
import com.redemonitor.main.components.DispositivoMonitorEscalonador.MonitorInfo;
import com.redemonitor.main.dto.request.SaveConfigRequest;
import com.redemonitor.main.dto.response.ConfigResponse;
import com.redemonitor.main.dto.response.MonitorServerResponse;
import com.redemonitor.main.mapper.ConfigMapper;
import com.redemonitor.main.mapper.MonitorServerMapper;
import com.redemonitor.main.model.Config;
import com.redemonitor.main.model.MonitorServer;
import com.redemonitor.main.repository.ConfigRepository;
import com.redemonitor.main.repository.MonitorServerRepository;

import jakarta.transaction.Transactional;

@Service
public class ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private MonitorServerRepository monitorServerRepository;
    
    @Autowired
    private MonitorServerMapper monitorServerMapper;
    
    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private DispositivoMonitorEscalonador dispositivoMonitorEscalonador;
    
    @Transactional
    public void updateConfig( SaveConfigRequest request ) {
        request.validate();

        Config config = configRepository.findFirstByOrderByIdAsc();                       	
        configMapper.load( config, request );        
        configRepository.save( config );
        
        dispositivoMonitorEscalonador.updateConfigInMonitores( config );
    }

    public ConfigResponse getConfig( boolean isLoadMonitorServer ) {
        Config config = configRepository.findFirstByOrderByIdAsc();
        
        ConfigResponse resp = configMapper.map( config );

        if ( isLoadMonitorServer ) {
	        List<MonitorServer> monitorServers = monitorServerRepository.findAll();
	        List<MonitorServerResponse> monitorServerResps = monitorServers.stream().map( monitorServerMapper::map ).toList();
	                        
	        for( MonitorServerResponse server : monitorServerResps ) {
	        	String host = server.getHost();
	        	MonitorInfo info = dispositivoMonitorEscalonador.getInfo( host );
	        	
	        	monitorServerMapper.load( server, info ); 
	        }
	        
	        resp.setMonitorServers( monitorServerResps );
        }
        
        return resp;
    }

}
