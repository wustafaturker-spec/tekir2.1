package com.ut.tekir.common.entity;

import com.ut.tekir.common.enums.FinanceActionType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Tax Transaction entity.
 */
@Entity
@Table(name = "TAX_TXN")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TaxTxn extends TxnBase {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "TAX_ID")
    private Tax tax;

    @Column(name = "ACTION_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private FinanceActionType actionType = FinanceActionType.Capital;
}
