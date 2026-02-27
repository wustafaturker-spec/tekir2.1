package com.ut.tekir.common.entity;

import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.embeddable.WorkBunch;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.FinanceAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ACCOUNT_TXN")
@Getter
@Setter
public class AccountTxn extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(name = "SERIAL", length = 30)
    private String serial;

    @Column(name = "REFERENCE", length = 30)
    private String reference;

    @Column(name = "TXN_DATE")
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(name = "INFO", length = 255)
    private String info;

    @Column(name = "CODE", length = 20)
    private String code;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "DOCUMENT_TYPE")
    private DocumentType documentType;

    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ACTION")
    private FinanceAction action; // Debit, Credit

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="value", column=@Column(name="AMOUNT_VALUE")),
        @AttributeOverride(name="currency", column=@Column(name="AMOUNT_CCY")),
        @AttributeOverride(name="localAmount", column=@Column(name="AMOUNT_LCY_VAL"))
    })
    private MoneySet amount = new MoneySet();
    
    @Column(name = "IS_ACTIVE")
    private Boolean active = Boolean.TRUE;
    
    @Embedded
    private WorkBunch workBunch = new WorkBunch();
}
