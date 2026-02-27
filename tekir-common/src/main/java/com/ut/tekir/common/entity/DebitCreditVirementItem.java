package com.ut.tekir.common.entity;

import com.ut.tekir.common.embeddable.MoneySet;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "DEBIT_CREDIT_VIREMENT_ITEM") @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
public class DebitCreditVirementItem extends AuditBase {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq") @Column(name = "ID") private Long id;
    @ManyToOne @JoinColumn(name = "OWNER_ID") private DebitCreditVirement owner;
    @Column(name = "INFO") private String info;
    @Embedded @AttributeOverrides({
        @AttributeOverride(name = "currency", column = @Column(name = "AMOUNT_CCY")),
        @AttributeOverride(name = "value", column = @Column(name = "AMOUNT_VALUE")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "AMOUNT_LCYVAL"))
    }) private MoneySet amount = new MoneySet();
}
