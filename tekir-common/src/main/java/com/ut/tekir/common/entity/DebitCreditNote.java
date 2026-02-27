package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.DocumentBase;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.FinanceAction; // Debit/Credit
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DEBIT_CREDIT_NOTE")
@Getter
@Setter
public class DebitCreditNote extends DocumentBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "FINANCE_ACTION")
    private FinanceAction action; 

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DebitCreditNoteItem> items = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.DebitCreditNote; 
    }
}

