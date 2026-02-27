package com.ut.tekir.common.entity;

import com.ut.tekir.common.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "ACCOUNT_CREDIT_NOTE") @Getter @Setter
public class AccountCreditNote extends DocumentBase {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq") @Column(name = "ID") private Long id;
    @ManyToOne @JoinColumn(name = "ACCOUNT_ID") private Account account;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountCreditNoteDetail> details = new ArrayList<>();
    @Override public Long getId() { return id; }
    @Override public DocumentType getDocumentType() { return DocumentType.AccountCreditNote; }
}
