package com.redemonitor.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String username;

    private String senha;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade=CascadeType.ALL)
    private List<UsuarioGrupoMap> grupos;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="empresa_id")
    private Empresa empresa;

}
