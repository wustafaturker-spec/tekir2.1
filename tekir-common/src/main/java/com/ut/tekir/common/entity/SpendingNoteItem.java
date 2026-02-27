package com.ut.tekir.common.entity;

import com.ut.tekir.common.embeddable.MoneySet;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Spending Note item detail.
 */
@Entity
@Table(name = "SPENDING_NOTE_ITEM")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class SpendingNoteItem extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private SpendingNote owner;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "INFO")
    private String info;

    @Column(name = "LINE_CODE", length = 10)
    private String lineCode;

    @Column(name = "QUANTITY", precision = 19, scale = 2)
    private BigDecimal quantity = BigDecimal.ZERO;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "currency", column = @Column(name = "PRICE_CCY")),
        @AttributeOverride(name = "value", column = @Column(name = "PRICE_VALUE")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "PRICE_LCYVAL"))
    })
    private MoneySet unitPrice = new MoneySet();

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "currency", column = @Column(name = "AMOUNT_CCY")),
        @AttributeOverride(name = "value", column = @Column(name = "AMOUNT_VALUE")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "AMOUNT_LCYVAL"))
    })
    private MoneySet amount = new MoneySet();
}
