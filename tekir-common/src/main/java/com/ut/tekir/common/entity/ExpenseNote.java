package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.DocumentBase;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.TradeAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EXPENSE_NOTE")
@Getter
@Setter
public class ExpenseNote extends DocumentBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;
    
    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account; // Payment account
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseItem> items = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.ExpenseNote; // Exists in Step 1366
    }
}
