package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Payment contract entity.
 */
@Entity
@Table(name = "PAYMENT_CONTRACT")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PaymentContract extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 100)
    @Size(max = 100)
    private String name;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @OneToMany(mappedBy = "paymentContract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentContractPeriod> periods = new ArrayList<>();
}
