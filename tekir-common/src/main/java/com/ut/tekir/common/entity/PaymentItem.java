package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.embeddable.MoneySet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PAYMENT_ITEM")
@Getter
@Setter
public class PaymentItem extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_ID")
    private Payment payment;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="value", column=@Column(name="AMOUNT_VALUE")),
        @AttributeOverride(name="currency", column=@Column(name="AMOUNT_CCY")),
        @AttributeOverride(name="localAmount", column=@Column(name="AMOUNT_LCYVAL"))
    })
    private MoneySet amount = new MoneySet();

    @Column(name = "INFO")
    private String info;
    
    @Column(name = "LINE_CODE")
    private String lineCode;
    
}
