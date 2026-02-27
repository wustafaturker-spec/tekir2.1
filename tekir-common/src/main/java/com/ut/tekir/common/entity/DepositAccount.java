package com.ut.tekir.common.entity;

import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "DEPOSIT_ACCOUNT") @Getter @Setter
public class DepositAccount extends DocumentBase {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq") @Column(name = "ID") private Long id;
    @ManyToOne @JoinColumn(name = "BANK_ACCOUNT_ID") private BankAccount bankAccount;
    @Override public Long getId() { return id; }
    @Override public DocumentType getDocumentType() { return DocumentType.DepositAccount; }
}
