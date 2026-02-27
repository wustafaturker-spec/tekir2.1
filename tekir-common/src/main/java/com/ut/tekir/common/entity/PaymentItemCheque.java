package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Payment item cheque link entity.
 */
@Entity
@Table(name = "PAYMENT_ITEM_CHEQUE")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PaymentItemCheque extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_ITEM_ID")
    private PaymentItem paymentItem;

    @ManyToOne
    @JoinColumn(name = "CHEQUE_ID")
    private Cheque cheque;
}
