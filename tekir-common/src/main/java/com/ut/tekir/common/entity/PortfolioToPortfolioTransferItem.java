package com.ut.tekir.common.entity;

import com.ut.tekir.common.embeddable.MoneySet;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Portfolio to Portfolio Transfer item entity.
 */
@Entity
@Table(name = "PORTFOLIO_TO_PORTFOLIO_TRNITM")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PortfolioToPortfolioTransferItem extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private PortfolioToPortfolioTransfer owner;

    @ManyToOne
    @JoinColumn(name = "SECURITY_ID")
    private Security security;

    @Column(name = "QUANTITY", precision = 19, scale = 2)
    private BigDecimal quantity = BigDecimal.ZERO;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "currency", column = @Column(name = "AMOUNT_CCY")),
        @AttributeOverride(name = "value", column = @Column(name = "AMOUNT_VALUE")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "AMOUNT_LCYVAL"))
    })
    private MoneySet amount = new MoneySet();
}
