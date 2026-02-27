package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Metrical conversion entity for unit conversions.
 */
@Entity
@Table(name = "METRICAL_CONVERT")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class MetricalConvert extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FROM_UNIT_ID")
    private Unit fromUnit;

    @ManyToOne
    @JoinColumn(name = "TO_UNIT_ID")
    private Unit toUnit;

    @Column(name = "FACTOR", precision = 19, scale = 6)
    private BigDecimal factor;
}
