package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Interest slice entity.
 */
@Entity
@Table(name = "INTEREST_SLICE")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class InterestSlice extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "INTEREST_ID")
    private Interest interest;

    @Column(name = "FROM_DATE")
    private LocalDate fromDate;

    @Column(name = "TO_DATE")
    private LocalDate toDate;

    @Column(name = "RATE", precision = 5, scale = 2)
    private BigDecimal rate;
}
