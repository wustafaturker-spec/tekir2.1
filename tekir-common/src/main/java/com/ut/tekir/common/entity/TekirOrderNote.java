package com.ut.tekir.common.entity;

import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.TradeAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TEKIR_ORDER_NOTE")
@Getter
@Setter
public class TekirOrderNote extends DocumentBase {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;
    @Column(name = "TRADE_ACTION")
    @Enumerated(EnumType.ORDINAL)
    private TradeAction tradeAction;
    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.PurchaseOrder;
    }
}
