package com.redemonitor.service.device;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.ScheduledFuture;

@AllArgsConstructor
@Getter
public class DispositivoMonitor {

    private DeviceMonitorThread deviceMonitorThread;
    private ScheduledFuture<?> scheduledFuture;

}
