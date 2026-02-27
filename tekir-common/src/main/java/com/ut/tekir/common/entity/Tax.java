package com.ut.tekir.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ut.tekir.common.enums.TaxType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Tax entity with child TaxRate list.
 */
@Entity
@Table(name = "TAX")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Tax implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", nullable = false, length = 10)
    @NotNull
    @Size(min = 1, max = 10)
    private String code;

    @Column(name = "NAME", length = 50)
    @Size(max = 50)
    private String name;

    @Column(name = "INFO")
    private String info;

    @Column(name = "TYPE")
    @Enumerated(EnumType.ORDINAL)
    private TaxType type;

    @Column(name = "`SYSTEM`")
    private Boolean system;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @Column(name = "IS_TRANSPORT_TAX")
    private Boolean isTransportTax = Boolean.FALSE;

    @JsonIgnore
    @OneToMany(mappedBy = "tax", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TaxRate> rates = new ArrayList<>();
}
