package com.ut.tekir.common.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.ut.tekir.common.enums.EDocumentType;
import com.ut.tekir.common.enums.EInvoiceDirection;
import com.ut.tekir.common.enums.EInvoiceStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * E-Invoice send/receive log entry.
 * Tracks every e-document operation for audit and troubleshooting.
 */
@Entity
@Table(name = "EINVOICE_LOG")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class EInvoiceLog extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID")
    private Invoice invoice;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIRECTION", length = 10, nullable = false)
    private EInvoiceDirection direction = EInvoiceDirection.OUTBOUND;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE", length = 20, nullable = false)
    private EDocumentType documentType = EDocumentType.EINVOICE;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    private EInvoiceStatus status = EInvoiceStatus.DRAFT;

    @Column(name = "UUID", length = 36)
    private String uuid;

    @Column(name = "ENVELOPE_ID", length = 50)
    private String envelopeId;

    @Column(name = "PROVIDER_RESPONSE", columnDefinition = "TEXT")
    private String providerResponse;

    @Column(name = "ERROR_MESSAGE", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "SENT_AT")
    private LocalDateTime sentAt;

    @Column(name = "RECEIVED_AT")
    private LocalDateTime receivedAt;
}
