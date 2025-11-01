package com.redemonitor.disp_monitor.service.device;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.ScheduledFuture;

@AllArgsConstructor
@Getter
public class DispositivoMonitor {

    private DispositivoMonitorThread deviceMonitorThread;
    private ScheduledFuture<?> scheduledFuture;

}
