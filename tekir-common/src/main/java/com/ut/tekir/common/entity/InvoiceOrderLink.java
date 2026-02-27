package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Invoice-Order link entity.
 */
@Entity
@Table(name = "INVOICE_ORDER_LINK")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class InvoiceOrderLink extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "ORDER_NOTE_ID")
    private OrderNote orderNote;
}
