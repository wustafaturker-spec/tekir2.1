package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.enums.FinanceAction;
import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * General Finance Transaction Log (Kasa/Banka Hareketleri).
 */
@Entity
@Table(name = "FINANCE_TXN")
@Getter
@Setter
public class FinanceTxn extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="value", column=@Column(name="AMOUNT_VALUE")),
        @AttributeOverride(name="currency", column=@Column(name="AMOUNT_CCY")),
        @AttributeOverride(name="localAmount", column=@Column(name="AMOUNT_LCYVAL"))
    })
    private MoneySet amount = new MoneySet();

    @Column(name = "TXN_DATE")
    private LocalDate date;
    
    @Column(name = "INFO")
    private String info;

    @Column(name = "ACTION")
    @Enumerated(EnumType.ORDINAL)
    private FinanceAction action;
    
    @Column(name = "DOCUMENT_TYPE")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    @Column(name = "CODE", length = 20)
    private String code; // Document code

}


