package com.redemonitor.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int sucessosQuant;

    private int falhasQuant;

    private int quedasQuant;

    private int tempoInatividade;

    private int duracao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date criadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dispositivo_id")
    private Dispositivo dispositivo;

}
