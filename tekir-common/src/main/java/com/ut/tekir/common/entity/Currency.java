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
 * Currency entity. Preserves CURRENCIES table structure.
 */
@Entity
@Table(name = "CURRENCIES")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Currency extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", length = 3, nullable = false, unique = true)
    @NotNull
    @Size(min = 1, max = 3)
    private String code;

    @Column(name = "NAME", length = 40)
    @Size(max = 40)
    private String name;

    @Column(name = "COUNTRY", length = 40)
    @Size(max = 40)
    private String country;

    @Column(name = "SYMBOL", length = 4)
    @Size(max = 4)
    private String symbol;

    @Column(name = "PIP")
    private Integer pip;

    @Column(name = "`SYSTEM`")
    private Boolean system;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @Column(name = "DECIMAL_CODE", length = 3)
    @Size(max = 3)
    private String decimalCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getPip() {
        return pip;
    }

    public void setPip(Integer pip) {
        this.pip = pip;
    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDecimalCode() {
        return decimalCode;
    }

    public void setDecimalCode(String decimalCode) {
        this.decimalCode = decimalCode;
    }
}
