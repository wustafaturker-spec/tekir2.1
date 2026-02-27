package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.embeddable.Quantity;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.embeddable.TaxEmbeddable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ORDER_ITEM")
@Getter
@Setter
public class OrderItem extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private OrderNote owner;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    
    @Embedded
    private Quantity quantity = new Quantity();
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "UNIT_PRICE_VAL")),
        @AttributeOverride(name = "currency", column = @Column(name = "UNIT_PRICE_CCY")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "UNIT_PRICE_LCYVAL"))
    })
    private MoneySet unitPrice = new MoneySet();
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "TOTAL_AMOUNT_VAL")),
        @AttributeOverride(name = "currency", column = @Column(name = "TOTAL_AMOUNT_CCY")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "TOTAL_AMOUNT_LCYVAL"))
    })
    private MoneySet amount = new MoneySet();
    
    @Embedded
    private TaxEmbeddable tax = new TaxEmbeddable(); // Assuming TaxEmbeddable exists (Batch 0)
    
    @Column(name = "INFO")
    private String info;

}
