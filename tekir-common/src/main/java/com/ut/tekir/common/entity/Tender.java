package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.DocumentBase;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.TenderStatus;
import com.ut.tekir.common.enums.TradeAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TENDER")
@Getter
@Setter
public class Tender extends DocumentBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "TRADE_ACTION")
    private TradeAction action; // Sale (Teklif Verme) or Purchase (Teklif Alma)

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "STATUS")
    private TenderStatus status = TenderStatus.Open;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TenderItem> items = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.SaleOrder;
    }
}
