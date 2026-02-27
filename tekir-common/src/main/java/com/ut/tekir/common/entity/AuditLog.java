package com.ut.tekir.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Audit log entity for tracking system changes.
 */
@Entity
@Table(name = "AUDIT_LOG")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class AuditLog extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACTION", length = 20)
    @Size(max = 20)
    private String action;

    @Column(name = "ENTITY_NAME", length = 100)
    @Size(max = 100)
    private String entityName;

    @Column(name = "ENTITY_ID")
    private Long entityId;

    @Column(name = "OLD_VALUE", columnDefinition = "TEXT")
    private String oldValue;

    @Column(name = "NEW_VALUE", columnDefinition = "TEXT")
    private String newValue;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;
}
