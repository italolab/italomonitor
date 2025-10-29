package com.redemonitor.main.dto.response;

import lombok.*;

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
