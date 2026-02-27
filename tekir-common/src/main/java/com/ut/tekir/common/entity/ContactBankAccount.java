package com.ut.tekir.common.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Contact bank account entity. Links contact to bank account info.
 * Note: BankAccount entity reference removed (will be a Long FK until BankAccount is migrated).
 */
@Entity
@Table(name = "CONTACT_BANK_ACCOUNT")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ContactBankAccount extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Contact owner;

    @Column(name = "BANK_ACCOUNT_ID")
    private Long bankAccountId;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;
}
