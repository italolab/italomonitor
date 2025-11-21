package italo.italomonitor.disp_monitor.service.device;

import java.util.concurrent.ScheduledFuture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DispositivoMonitor {

    private DispositivoMonitorManager deviceMonitorManager;
    private ScheduledFuture<?> scheduledFuture;

}
