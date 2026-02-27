package com.ut.tekir.common.entity;

import com.ut.tekir.common.enums.AdvanceProcessType;
import com.ut.tekir.common.enums.FinanceActionType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Bank Transaction entity.
 * Extends TxnBase which already has: id, date, action, amount, documentType, documentId, etc.
 */
@Entity
@Table(name = "BANK_TXN")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class BankTxn extends TxnBase {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "BANK_ACCOUNT_ID")
    private BankAccount bankAccount;

    @Column(name = "ACTION_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private FinanceActionType actionType = FinanceActionType.Capital;

    @Column(name = "PROCESS_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private AdvanceProcessType processType = AdvanceProcessType.Normal;
}
