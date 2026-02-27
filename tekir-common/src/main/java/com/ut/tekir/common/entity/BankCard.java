package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Bank card entity.
 */
@Entity
@Table(name = "BANK_CARD")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class BankCard extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CARD_NO", length = 20)
    @Size(max = 20)
    private String cardNo;

    @Column(name = "CARD_HOLDER", length = 50)
    @Size(max = 50)
    private String cardHolder;

    @ManyToOne
    @JoinColumn(name = "BANK_ACCOUNT_ID")
    private BankAccount bankAccount;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;
}
