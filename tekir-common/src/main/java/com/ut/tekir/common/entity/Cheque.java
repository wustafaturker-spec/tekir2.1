package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.DocumentBase;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.ChequeStatus;
import com.ut.tekir.common.enums.ChequeDirection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "CHEQUE")
@Getter
@Setter
public class Cheque extends DocumentBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MATURITY_DATE")
    private LocalDate maturityDate;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="value", column=@Column(name="AMOUNT_VALUE")),
        @AttributeOverride(name="currency", column=@Column(name="AMOUNT_CCY")),
        @AttributeOverride(name="localAmount", column=@Column(name="AMOUNT_LCYVAL"))
    })
    private MoneySet amount = new MoneySet();

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "STATUS")
    private ChequeStatus status = ChequeStatus.Portfoy;
    
    @Column(name = "CHEQUE_OWNER")
    private String chequeOwner;
    
    @Column(name = "BANK_NAME")
    private String bankName;
    
    @Column(name = "BANK_BRANCH")
    private String bankBranch;
    
    @Column(name = "ACCOUNT_NO")
    private String accountNo;
    
    @Column(name = "IBAN")
    private String iban;
    
    @Column(name = "PAYMENT_PLACE")
    private String paymentPlace;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "DIRECTION")
    private ChequeDirection direction = ChequeDirection.Incoming;

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.ChequeStatusChanging;
    }
}
