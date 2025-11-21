package italo.italomonitor.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import italo.italomonitor.main.components.DispositivoMonitorEscalonador;
import italo.italomonitor.main.components.DispositivoMonitorEscalonador.MonitorInfo;
import italo.italomonitor.main.dto.request.SaveConfigRequest;
import italo.italomonitor.main.dto.response.ConfigResponse;
import italo.italomonitor.main.dto.response.MonitorServerResponse;
import italo.italomonitor.main.dto.response.NoAdminConfigResponse;
import italo.italomonitor.main.mapper.ConfigMapper;
import italo.italomonitor.main.mapper.MonitorServerMapper;
import italo.italomonitor.main.model.Config;
import italo.italomonitor.main.model.MonitorServer;
import italo.italomonitor.main.repository.ConfigRepository;
import italo.italomonitor.main.repository.MonitorServerRepository;
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

    public NoAdminConfigResponse getNoAdminConfig() {
    	Config config = configRepository.findFirstByOrderByIdAsc();
    	
    	return configMapper.mapToNoAdmin( config );
    }
    
}
