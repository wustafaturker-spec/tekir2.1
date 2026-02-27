package com.ut.tekir.common.entity;

import com.ut.tekir.common.enums.FinanceActionType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Foundation Transaction entity.
 */
@Entity
@Table(name = "FOUNDATION_TXN")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class FoundationTxn extends TxnBase {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "FOUNDATION_ID")
    private Foundation foundation;

    @Column(name = "ACTION_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private FinanceActionType actionType = FinanceActionType.Capital;
}
