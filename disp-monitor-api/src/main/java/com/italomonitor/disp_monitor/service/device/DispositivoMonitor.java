package com.italomonitor.disp_monitor.service.device;

import java.util.concurrent.ScheduledFuture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DispositivoMonitor {

    private DispositivoMonitorThread deviceMonitorThread;
    private ScheduledFuture<?> scheduledFuture;

}
