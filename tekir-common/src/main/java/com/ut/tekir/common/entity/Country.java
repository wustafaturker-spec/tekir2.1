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
 * Country entity.
 */
@Entity
@Table(name = "COUNTRY")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Country extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", nullable = false, unique = true, length = 3)
    @NotNull
    @Size(min = 1, max = 3)
    private String code;

    @Column(name = "NAME", length = 30)
    @Size(max = 30)
    private String name;

    @Column(name = "NAME_EN", length = 30)
    @Size(max = 30)
    private String nameInternational;

    @Column(name = "INFO")
    private String info;

    @Column(name = "`SYSTEM`")
    private Boolean system;

    @Column(name = "WEIGHT")
    private Integer weight;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;
}
