package com.redemonitor.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConfigResponse {

    private int maxFalhasConsecutivas;
    private int numPacotesPorVez;
    private int monitoramentoDelay;

}
