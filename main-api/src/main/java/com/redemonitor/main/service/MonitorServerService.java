package com.redemonitor.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redemonitor.main.components.DispositivoMonitorEscalonador;
import com.redemonitor.main.components.DispositivoMonitorEscalonador.MonitorInfo;
import com.redemonitor.main.dto.request.SaveMonitorServerRequest;
import com.redemonitor.main.dto.response.MonitorServerResponse;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.mapper.MonitorServerMapper;
import com.redemonitor.main.model.MonitorServer;
import com.redemonitor.main.repository.MonitorServerRepository;

@Service
public class MonitorServerService {

    @Autowired
    private MonitorServerRepository monitorServerRepository;

    @Autowired
    private MonitorServerMapper monitorServerMapper;
    
    @Autowired
    private DispositivoMonitorEscalonador dispositivoMonitorEscalonador;

    public void createMonitorServer( SaveMonitorServerRequest request ) {
        request.validate();

        MonitorServer monitorServer = monitorServerMapper.map( request );
        String host = monitorServer.getHost();

        Optional<MonitorServer> monitorServerOp = monitorServerRepository.findByHost( host );
        if ( monitorServerOp.isPresent() )
            throw new BusinessException( Errors.MONITOR_SERVER_ALREADY_EXISTS );

        monitorServerRepository.save( monitorServer );
    }

    public void updateMonitorServer( Long id, SaveMonitorServerRequest request ) {
        request.validate();

        String host = request.getHost();

        Optional<MonitorServer> monitorServerOp = monitorServerRepository.findById( id );
        if ( monitorServerOp.isEmpty() )
            throw new BusinessException( Errors.MONITOR_SERVER_NOT_FOUND );

        MonitorServer monitorServer = monitorServerOp.get();
        if ( !monitorServer.getHost().equalsIgnoreCase( host ) )
            if ( monitorServerRepository.findByHost( host ).isPresent() )
                throw new BusinessException( Errors.MONITOR_SERVER_ALREADY_EXISTS );

        monitorServerMapper.load( monitorServer, request );

        monitorServerRepository.save( monitorServer );
    }

    public List<MonitorServerResponse> filterMonitorServers( String hostPart, String accessToken ) {
        List<MonitorServer> monitorServers = monitorServerRepository.filter( "%"+hostPart+"%" );
        
        List<MonitorServerResponse> responses = new ArrayList<>();
        for( MonitorServer server : monitorServers )
        	responses.add( this.buildMonitorServer( server, accessToken ) );
        return responses;
    }

    public MonitorServerResponse getMonitorServer( Long id, String accessToken ) {
        Optional<MonitorServer> monitorServerOp = monitorServerRepository.findById( id );
        if ( monitorServerOp.isEmpty() )
            throw new BusinessException( Errors.MONITOR_SERVER_NOT_FOUND );

        MonitorServer monitorServer = monitorServerOp.get();
        return this.buildMonitorServer( monitorServer, accessToken );        
    }

    public void deleteMonitorServer( Long id ) {
        Optional<MonitorServer> monitorServerOp = monitorServerRepository.findById( id );
        if ( monitorServerOp.isEmpty() )
            throw new BusinessException( Errors.MONITOR_SERVER_NOT_FOUND );

        monitorServerRepository.deleteById( id );
    }

    private MonitorServerResponse buildMonitorServer( MonitorServer monitorServer, String accessToken ) {    	
    	MonitorServerResponse resp = monitorServerMapper.map( monitorServer );
    	        
      	String host = monitorServer.getHost();
       	MonitorInfo info = dispositivoMonitorEscalonador.getInfo( host, accessToken );
        	
        monitorServerMapper.load( resp, info ); 
        
        return resp;
    }
    
}
