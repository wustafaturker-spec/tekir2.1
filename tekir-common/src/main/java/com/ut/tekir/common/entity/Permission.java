package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Permission entity — maps to PERMISSION table.
 * Stores fine.subtract(grained) authorities like "contact:create", "product:update".
 * Referenced by ROLE_PERMISSION join table.
 */
@Entity
@Table(name = "PERMISSION")
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PERMISSION_CODE", nullable = false, unique = true, length = 50)
    private String permissionCode;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    @Column(name = "MODULE", length = 50)
    private String module;
}

