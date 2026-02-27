package com.ut.tekir.common.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.ut.tekir.common.enums.ProductType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Product entity — core product definition.
 * Note: Some ManyToOne references (ExpenseType, etc.) deferred as Long FKs.
 */
@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Product extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private ProductType productType = ProductType.Product;

    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    @NotNull
    @Size(min = 1, max = 20)
    private String code;

    @Column(name = "NAME", length = 80)
    @Size(max = 80)
    private String name;

    @Column(name = "INFO")
    private String info;

    @Column(name = "OPEN_DATE")
    private LocalDate openDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    private ProductCategory category;

    @Column(name = "`SYSTEM`")
    private Boolean system;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @Column(name = "UNIT", length = 10)
    @Size(max = 10)
    private String unit;

    @Column(name = "BARCODE1", length = 80)
    @Size(max = 80)
    private String barcode1;

    @Column(name = "BARCODE2", length = 80)
    @Size(max = 80)
    private String barcode2;

    @Column(name = "BARCODE3", length = 80)
    @Size(max = 80)
    private String barcode3;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "ExpenseType_ID")
    private Long expenseTypeId;

    @ManyToOne
    @JoinColumn(name = "BUY_TAX_ID")
    private Tax buyTax;

    @ManyToOne
    @JoinColumn(name = "SELL_TAX_ID")
    private Tax sellTax;

    @Column(name = "WEIGHT")
    private Integer weight;

    @Column(name = "GROUP_CODE", length = 20)
    @Size(max = 20)
    private String groupCode;
}
