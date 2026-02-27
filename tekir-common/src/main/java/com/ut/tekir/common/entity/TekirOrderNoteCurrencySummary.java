package com.ut.tekir.common.entity;

import com.ut.tekir.common.embeddable.MoneySet;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "TEKIR_ORDER_NOTE_CURRENCY_SUMMARY") @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
public class TekirOrderNoteCurrencySummary extends AuditBase {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq") @Column(name = "ID") private Long id;
    @ManyToOne @JoinColumn(name = "OWNER_ID") private TekirOrderNote owner;
    @Embedded @AttributeOverrides({
        @AttributeOverride(name = "currency", column = @Column(name = "TOTAL_CCY")),
        @AttributeOverride(name = "value", column = @Column(name = "TOTAL_VALUE")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "TOTAL_LCYVAL"))
    }) private MoneySet total = new MoneySet();
}
