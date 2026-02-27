package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Role-Permission mapping entity.
 */
@Entity
@Table(name = "ROLE_PERMISSION")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class RolePermission extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "PERMISSION_ID")
    private Permission permission;
}
