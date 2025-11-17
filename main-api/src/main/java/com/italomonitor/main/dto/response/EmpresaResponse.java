package com.italomonitor.main.dto.response;

import java.util.Date;

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
public class EmpresaResponse {

    private Long id;
    
    private String nome;
    
    private String emailNotif;
    
    private String telegramChatId;
    
    private double porcentagemMaxFalhasPorLote;
    
    private int maxDispositivosQuant;
    
    private int minTempoParaProxNotif;
    
    private int diaPagto;
    
    private boolean temporario;
    
    private int usoTemporarioPor;
    
    private boolean bloqueada;
    
    private Date criadoEm;
    
    private Date usoRegularIniciadoEm;
    
    private Date pagoAte;

}
