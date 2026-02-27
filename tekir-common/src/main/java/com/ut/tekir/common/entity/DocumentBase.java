package com.ut.tekir.common.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.ut.tekir.common.enums.DocumentType;
import lombok.Getter;
import lombok.Setter;

/**
 * Base Document fields.
 * Preserves column mappings: SERIAL, CODE, INFO, REFERENCE, ISACTIVE,
 * TXNDATE, TXNTIME, RECEPIENT, DELIVERER, integration fields.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class DocumentBase extends AuditBase implements Document, Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "SERIAL", length = 10, nullable = false, unique = true)
    @NotNull
    @Size(min = 1, max = 10)
    private String serial;

    @Column(name = "CODE", length = 15)
    @Size(max = 15)
    private String code;

    @Column(name = "INFO")
    private String info;

    @Column(name = "REFERENCE", length = 10)
    @Size(max = 10)
    private String reference;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @Column(name = "TXNDATE")
    private LocalDate date;

    @Column(name = "TXNTIME")
    private LocalTime time;

    @Column(name = "RECEPIENT", length = 92)
    @Size(max = 92)
    private String recepient;

    @Column(name = "DELIVERER", length = 92)
    @Size(max = 92)
    private String deliverer;

    // Integration fields
    @Column(name = "INTEGRATION_DATE")
    private LocalDateTime integrationDate;

    @Column(name = "INTEGRATION_DOCUMENTID")
    private Long integrationDocumentId;

    @Column(name = "INTEGRATION_SERIAL", length = 10)
    @Size(max = 10)
    private String integrationSerial;

    @Column(name = "INTEGRATION_TEMPLATE_NAME")
    private String integrationTemplateName;

    public abstract DocumentType getDocumentType();

    public abstract Long getId();
}
