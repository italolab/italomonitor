package com.redemonitor.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DispositivoResponse {

    private Long id;
    private String host;
    private String nome;
    private String descricao;
    private String localizacao;
    private boolean sendoMonitorado;
    private EmpresaResponse empresa;

}
