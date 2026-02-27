package com.ut.tekir.common.entity;

import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Foreign Exchange document.
 */
@Entity
@Table(name = "FOREIGN_EXCHANGE")
@Getter
@Setter
public class ForeignExchange extends DocumentBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FROM_BANK_ACCOUNT_ID")
    private BankAccount fromBankAccount;

    @ManyToOne
    @JoinColumn(name = "TO_BANK_ACCOUNT_ID")
    private BankAccount toBankAccount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "localAmount", column = @Column(name = "FROMLCYVAL")),
        @AttributeOverride(name = "currency", column = @Column(name = "FROMCCY")),
        @AttributeOverride(name = "value", column = @Column(name = "FROMAMT"))
    })
    private MoneySet fromAmount = new MoneySet();

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "localAmount", column = @Column(name = "TOLCYVAL")),
        @AttributeOverride(name = "currency", column = @Column(name = "TOCCY")),
        @AttributeOverride(name = "value", column = @Column(name = "TOAMT"))
    })
    private MoneySet toAmount = new MoneySet();

    @Column(name = "EXCHANGE_RATE")
    private java.math.BigDecimal exchangeRate;

    @Override
    public Long getId() { return id; }

    @Override
    public DocumentType getDocumentType() { return DocumentType.ForeignExchange; }
}
