package com.redemonitor.service.device;

import com.redemonitor.model.Config;
import com.redemonitor.model.Dispositivo;
import lombok.Setter;

public class DeviceMonitorThread implements Runnable {

    private final Dispositivo dispositivo;

    @Setter
    private Config config;

    public DeviceMonitorThread( Dispositivo dispositivo, Config config ) {
        this.dispositivo = dispositivo;
        this.config = config;
    }

    public void run() {
        try {
            Thread.sleep( 5000 );
            System.out.println( "Dispositivo monitorado: "+dispositivo.getId() );
        } catch (InterruptedException e) {

        }
    }

}
