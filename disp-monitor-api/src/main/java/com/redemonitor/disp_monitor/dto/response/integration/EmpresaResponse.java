package com.redemonitor.disp_monitor.dto.response.integration;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmpresaResponse {

    private Long id;
    
    private String nome;
    
    private String emailNotif;
    
    private double porcentagemMaxFalhasPorLote;

}
