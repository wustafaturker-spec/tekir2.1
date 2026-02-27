package com.ut.tekir.common.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Province entity, child of City.
 */
@Entity
@Table(name = "PROVINCE")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Province extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PLATE", length = 6, nullable = false)
    @NotNull
    @Size(min = 1, max = 6)
    private String plate;

    @Column(name = "NAME", length = 40)
    @Size(max = 40)
    private String name;

    @Column(name = "WEIGHT")
    private Integer weight;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    @Column(name = "`SYSTEM`")
    private Boolean system;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;
}
