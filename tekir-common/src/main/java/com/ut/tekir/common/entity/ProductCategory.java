package com.ut.tekir.common.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Product category entity.
 */
@Entity
@Table(name = "PRODUCT_CATEGORY")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    @NotNull
    @Size(min = 1, max = 20)
    private String code;

    @Column(name = "INFO")
    private String info;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @Column(name = "`SYSTEM`")
    private Boolean system;

    @Column(name = "WEIGHT")
    private Integer weight;
}
