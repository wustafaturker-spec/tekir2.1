package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * POS-Bank list mapping entity.
 */
@Entity
@Table(name = "POS_BANK_LIST")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PosBankList extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POS_ID")
    private Pos pos;

    @ManyToOne
    @JoinColumn(name = "BANK_ID")
    private Bank bank;
}
