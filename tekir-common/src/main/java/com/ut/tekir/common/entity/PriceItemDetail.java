package com.ut.tekir.common.entity;

import com.ut.tekir.common.embeddable.MoneySet;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Price item detail entity.
 */
@Entity
@Table(name = "PRICE_ITEM_DETAIL")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PriceItemDetail extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRICE_LIST_ITEM_ID")
    private PriceListItem priceListItem;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "currency", column = @Column(name = "PRICE_CCY")),
        @AttributeOverride(name = "value", column = @Column(name = "PRICE_VALUE")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "PRICE_LCYVAL"))
    })
    private MoneySet price = new MoneySet();
}
