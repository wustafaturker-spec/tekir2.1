package com.ut.tekir.common.entity;

import com.ut.tekir.common.enums.FinanceActionType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * POS Transaction entity.
 */
@Entity
@Table(name = "POS_TXN")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class PosTxn extends TxnBase {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "POS_ID")
    private Pos pos;

    @Column(name = "ACTION_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private FinanceActionType actionType = FinanceActionType.Capital;
}
