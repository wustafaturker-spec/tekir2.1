package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * POS card list entity.
 */
@Entity
@Table(name = "POS_CARD_LIST")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PosCardList extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POS_ID")
    private Pos pos;

    @Column(name = "CARD_NAME", length = 50)
    @Size(max = 50)
    private String cardName;
}
