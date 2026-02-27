package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Product detail entity.
 */
@Entity
@Table(name = "PRODUCT_DETAIL")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ProductDetail extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "ATTR_KEY", length = 100)
    private String attrKey;

    @Column(name = "ATTR_VALUE", length = 250)
    private String attrValue;
}
