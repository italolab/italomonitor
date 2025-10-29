package com.redemonitor.main.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade=CascadeType.ALL)
    private List<RoleGrupoMap> grupos;

}
