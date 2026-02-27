package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.DocumentBase;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Fund Transfer Document (Kasa/Banka Virman).
 */
@Entity
@Table(name = "FUND_TRANSFER")
@Getter
@Setter
public class FundTransfer extends DocumentBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FROM_ACCOUNT_ID")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "TO_ACCOUNT_ID")
    private Account toAccount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="value", column=@Column(name="AMOUNT_VALUE")),
        @AttributeOverride(name="currency", column=@Column(name="AMOUNT_CCY")),
        @AttributeOverride(name="localAmount", column=@Column(name="AMOUNT_LCYVAL"))
    })
    private MoneySet amount = new MoneySet();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.FundTransfer;
    }
}


