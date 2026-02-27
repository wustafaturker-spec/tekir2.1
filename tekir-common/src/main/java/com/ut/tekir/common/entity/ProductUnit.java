package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Product unit mapping entity.
 */
@Entity
@Table(name = "PRODUCT_UNIT")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ProductUnit extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;
}
