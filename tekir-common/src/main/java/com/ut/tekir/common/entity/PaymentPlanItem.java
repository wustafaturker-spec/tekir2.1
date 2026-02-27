package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.AuditBase;
import com.ut.tekir.common.enums.PaymentPlanCalcType;
import com.ut.tekir.common.enums.PaymentPlanDestType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "PAYMENT_PLAN_ITEM")
@Getter
@Setter
public class PaymentPlanItem extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private PaymentPlan owner;

    @Column(name = "PAYMENT_DAY")
    private Integer day;
    
    @Column(name = "RATE", precision = 10, scale = 4)
    private BigDecimal rate = BigDecimal.ZERO;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "CALC_TYPE")
    private PaymentPlanCalcType calcType;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "DEST_TYPE")
    private PaymentPlanDestType destType;

}
