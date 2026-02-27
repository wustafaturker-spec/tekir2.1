package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.embeddable.MoneySet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PRICE_LIST_ITEM")
@Getter
@Setter
public class PriceListItem extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private PriceList owner;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    
    @Embedded
    private MoneySet price = new MoneySet();

}
