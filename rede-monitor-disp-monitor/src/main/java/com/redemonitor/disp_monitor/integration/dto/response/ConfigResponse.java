package com.redemonitor.disp_monitor.integration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConfigResponse {

    private Long id;

    private int monitoramentoDelay;

    private int numPacotesPorLote;

    private int registroEventoPeriodo;

}
