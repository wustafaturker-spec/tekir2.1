package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.DocumentBase;
import com.ut.tekir.common.enums.FinanceAction;
import com.ut.tekir.common.enums.AccountType;
import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Payment/Collection Document (Tahsilat/Tediye Fişi).
 */
@Entity
@Table(name = "PAYMENT")
@Getter
@Setter
public class Payment extends DocumentBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.Payment; // Assuming Payment is defined in DocumentType
    }

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(name = "ACTION")
    @Enumerated(EnumType.ORDINAL)
    private FinanceAction action; // DEBIT=Borç(Tediye), CREDIT=Alacak(Tahsilat)

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentItem> items = new ArrayList<>();

}
