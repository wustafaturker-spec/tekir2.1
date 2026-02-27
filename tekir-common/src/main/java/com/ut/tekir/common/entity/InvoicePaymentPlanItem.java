package com.ut.tekir.common.entity;

import com.ut.tekir.common.embeddable.MoneySet;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Invoice payment plan item entity.
 */
@Entity
@Table(name = "INVOICE_PAYMENTPLAN_ITEM")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class InvoicePaymentPlanItem extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_PLAN_ID")
    private PaymentPlan paymentPlan;

    @Column(name = "DUE_DATE")
    private LocalDate dueDate;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "currency", column = @Column(name = "AMOUNT_CCY")),
        @AttributeOverride(name = "value", column = @Column(name = "AMOUNT_VALUE")),
        @AttributeOverride(name = "localAmount", column = @Column(name = "AMOUNT_LCYVAL"))
    })
    private MoneySet amount = new MoneySet();
}
