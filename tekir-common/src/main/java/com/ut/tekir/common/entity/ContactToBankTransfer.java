package com.ut.tekir.common.entity;

import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.enums.BankTransferType;
import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Contact to Bank Transfer document.
 */
@Entity
@Table(name = "CONTACT_TO_BANK_TRANSFER")
@Getter
@Setter
public class ContactToBankTransfer extends DocumentBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "BANK_ID")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "BANK_BRANCH_ID")
    private BankBranch bankBranch;

    @ManyToOne
    @JoinColumn(name = "BANK_ACCOUNT_ID")
    private BankAccount bankAccount;

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

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "localAmount", column = @Column(name = "COSTLCYAMT")),
        @AttributeOverride(name = "currency", column = @Column(name = "COSTCCY")),
        @AttributeOverride(name = "value", column = @Column(name = "COSTAMT"))
    })
    private MoneySet cost = new MoneySet();

    @Column(name = "TRANSFER_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private BankTransferType transferType;

    @Override
    public Long getId() { return id; }

    @Override
    public DocumentType getDocumentType() { return DocumentType.ContactToBankTransfer; }
}
