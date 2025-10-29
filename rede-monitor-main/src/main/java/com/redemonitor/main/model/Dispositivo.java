package com.redemonitor.main.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.redemonitor.main.model.enums.DispositivoStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="dispositivo")
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String host;

    private String nome;

    private String descricao;

    private String localizacao;

    private boolean sendoMonitorado;

    @Enumerated(EnumType.STRING)
    private DispositivoStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="empresa_id")
    private Empresa empresa;

    @OneToMany(mappedBy = "dispositivo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Evento> eventos;

}
