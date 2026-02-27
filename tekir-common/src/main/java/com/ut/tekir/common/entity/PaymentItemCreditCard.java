package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Payment item credit card link entity.
 */
@Entity
@Table(name = "PAYMENT_ITEM_CREDIT_CARD")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PaymentItemCreditCard extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_ITEM_ID")
    private PaymentItem paymentItem;

    @ManyToOne
    @JoinColumn(name = "BANK_CARD_ID")
    private BankCard bankCard;
}
