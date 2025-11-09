package com.redemonitor.main.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="config")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int monitoramentoDelay;

    private int numPacotesPorLote;

    private int registroEventoPeriodo;
    
    private int numThreadsLimite;
        
    private int monitorServerCorrente;
    
    private String telegramBotToken;

}
