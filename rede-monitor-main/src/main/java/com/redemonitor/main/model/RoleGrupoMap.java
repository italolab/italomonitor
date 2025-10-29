package com.redemonitor.main.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="role_grupo_map")
public class RoleGrupoMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_grupo_id")
    private UsuarioGrupo usuarioGrupo;

}
