package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * User-Role mapping entity.
 */
@Entity
@Table(name = "USER_ROLE")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class UserRole extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;
}
