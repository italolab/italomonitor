package com.italomonitor.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.italomonitor.main.components.DispositivoMonitorEscalonador;
import com.italomonitor.main.components.DispositivoMonitorEscalonador.MonitorInfo;
import com.italomonitor.main.dto.request.SaveConfigRequest;
import com.italomonitor.main.dto.response.ConfigResponse;
import com.italomonitor.main.dto.response.MonitorServerResponse;
import com.italomonitor.main.mapper.ConfigMapper;
import com.italomonitor.main.mapper.MonitorServerMapper;
import com.italomonitor.main.model.Config;
import com.italomonitor.main.model.MonitorServer;
import com.italomonitor.main.repository.ConfigRepository;
import com.italomonitor.main.repository.MonitorServerRepository;

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
    
    @Autowired
    private SistemaService sistemaService;
    
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
        
        int numDispsSendoMonitorados = 0;
        
        if ( isLoadMonitorServer ) {
	        List<MonitorServer> monitorServers = monitorServerRepository.findAll();
	        List<MonitorServerResponse> monitorServerResps = monitorServers.stream().map( monitorServerMapper::map ).toList();
	                        
	        for( MonitorServerResponse server : monitorServerResps ) {
	        	String host = server.getHost();
	        	MonitorInfo info = dispositivoMonitorEscalonador.getInfo( host );
	        	if ( info.getInfo() != null )
	        		numDispsSendoMonitorados += info.getInfo().getNumThreadsAtivas();
	        	
	        	monitorServerMapper.load( server, info ); 
	        }
	        
	        resp.setMonitorServers( monitorServerResps );
        }
        
        resp.setNumDispositivosSendoMonitorados( numDispsSendoMonitorados ); 
        resp.setInfo( sistemaService.getInfo() );
        return resp;
    }

}
