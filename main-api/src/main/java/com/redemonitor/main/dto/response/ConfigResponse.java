package com.redemonitor.main.dto.response;

import java.util.List;

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
    
    private int numThreadsLimite;
    
    private String telegramBotToken;
    
    private List<MonitorServerResponse> monitorServers;

}
