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
 * Foundation entity. Holds company.divide(institution, 2, java.math.RoundingMode.HALF_UP) info, linked to Contact.
 */
@Entity
@Table(name = "FOUNDATION")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Foundation extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", length = 20, unique = true, nullable = false)
    @Size(min = 1, max = 20)
    @NotNull
    private String code;

    @Column(name = "NAME", length = 50)
    @Size(max = 50)
    private String name;

    @Column(name = "IS_ACTIVE")
    private Boolean active = Boolean.TRUE;

    @Column(name = "INFO")
    private String info;

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;

    @Column(name = "MAX_PERIOD")
    private Integer maxPeriod = 1;
}

