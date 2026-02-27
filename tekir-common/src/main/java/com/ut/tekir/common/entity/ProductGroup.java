package com.ut.tekir.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Product group entity.
 */
@Entity
@Table(name = "PRODUCT_GROUP")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ProductGroup extends AuditBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", length = 20)
    @Size(max = 20)
    private String code;

    @Column(name = "NAME", length = 50)
    @Size(max = 50)
    private String name;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;
}
