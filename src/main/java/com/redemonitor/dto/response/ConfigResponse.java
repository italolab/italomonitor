package com.redemonitor.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConfigResponse {

    private int monitoramentoDelay;

    private int numPacotesPorLote;

    private int registroEventoPeriodoSegundos;

}
