package com.ut.tekir.common.entity;

import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.TradeAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Spending Note (Gider Pusulası) document.
 */
@Entity
@Table(name = "SPENDING_NOTE")
@Getter
@Setter
public class SpendingNote extends DocumentBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "WAREHOUSE_ID")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpendingNoteItem> items = new ArrayList<>();

    @Column(name = "TRADE_ACTION")
    @Enumerated(EnumType.ORDINAL)
    private TradeAction action;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.SpendingNote;
    }
}
