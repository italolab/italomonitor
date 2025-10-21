package com.redemonitor.service.device;

import com.redemonitor.model.Config;
import lombok.Setter;

public class DeviceMonitorThread implements Runnable {

    private final Long dispositivoId;

    @Setter
    private Config config;

    public DeviceMonitorThread( Long dispositivoId, Config config ) {
        this.dispositivoId = dispositivoId;
        this.config = config;
    }

    public void run() {
        try {
            Thread.sleep( 5000 );
        } catch (InterruptedException e) {

        }
    }

}
