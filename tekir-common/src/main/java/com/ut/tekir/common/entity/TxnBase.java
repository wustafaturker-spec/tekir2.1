package com.ut.tekir.common.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.FinanceAction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for transaction records (Txn tables).
 * Preserves column mappings from old Tekir.
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class TxnBase extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "TXN_DATE")
    private LocalDate date;

    @Column(name = "FINANCE_ACTION")
    @Enumerated(EnumType.ORDINAL)
    private FinanceAction action;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @Column(name = "CODE", length = 15)
    @Size(max = 15)
    private String code;

    @Column(name = "INFO")
    private String info;

    @Column(name = "SERIAL", length = 10)
    @Size(max = 10)
    private String serial;

    @Column(name = "REFERENCE", length = 10)
    @Size(max = 10)
    private String reference;

    @Column(name = "DOCUMENT_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private DocumentType documentType;

    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    @Column(name = "ADVERSE_CODE", length = 20)
    @Size(max = 20)
    private String adverseCode;

    @Column(name = "ADVERSE_NAME", length = 250)
    @Size(max = 250)
    private String adverseName;

    @Embedded
    @Valid
    @AttributeOverrides({
        @AttributeOverride(name = "currency", column = @Column(name = "CCY")),
        @AttributeOverride(name = "value", column = @Column(name = "CCYVAL")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "LCYVAL"))
    })
    private MoneySet amount = new MoneySet();

    @Column(name = "REPAID_STATUS")
    private boolean repaidStatus = false;

    @Column(name = "REFERENCE_ID")
    private Long referenceId;

    @Column(name = "REFERENCE_DOC_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private DocumentType referenceDocumentType;
}
