package com.ut.tekir.common.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.ut.tekir.common.embeddable.Address;
import com.ut.tekir.common.embeddable.ContactPerson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Warehouse entity with embedded Address and ContactPerson.
 */
@Entity
@Table(name = "WAREHOUSE")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Warehouse extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", length = 20, nullable = false, unique = true)
    @NotNull
    @Size(min = 1, max = 20)
    private String code;

    @Column(name = "NAME", length = 50)
    @Size(min = 1, max = 50)
    private String name;

    @Column(name = "INFO")
    private String info;

    @Column(name = "`SYSTEM`")
    private Boolean system = Boolean.FALSE;

    @Column(name = "ISACTIVE")
    private Boolean active = Boolean.TRUE;

    @Embedded
    @Valid
    private Address address = new Address();

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    @ManyToOne
    @JoinColumn(name = "PROVINCE_ID")
    private Province province;

    @Embedded
    private ContactPerson contactPerson = new ContactPerson();

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organization organization;
}
