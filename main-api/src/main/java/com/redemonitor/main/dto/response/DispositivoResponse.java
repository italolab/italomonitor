package com.redemonitor.main.dto.response;

import com.redemonitor.main.enums.DispositivoStatus;

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
    private DispositivoStatus status;
    private EmpresaResponse empresa;

}
