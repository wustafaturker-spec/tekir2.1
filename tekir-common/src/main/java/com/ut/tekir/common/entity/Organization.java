package com.ut.tekir.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.ut.tekir.common.embeddable.Address;
import com.ut.tekir.common.embeddable.ContactPerson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Hierarchical organization entity with embedded Address and ContactPerson.
 * Note: @Cascade(DELETE_ORPHAN) → orphanRemoval = true
 */
@Entity
@Table(name = "ORGANIZATION")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    @NotNull
    private String code;

    @Column(name = "NAME", length = 30)
    @NotNull
    private String name;

    @Column(name = "IS_ACTIVE")
    private Boolean active = Boolean.TRUE;

    @Column(name = "HAS_CHILD")
    private Boolean hasChild = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "PARENT_ORGANIZATION")
    private Organization parentOrganization;

    @ManyToOne
    @JoinColumn(name = "LEVEL_ID")
    private OrganizationLevel level;

    @Column(name = "HAS_PRIVATE_ADDRESS")
    private Boolean hasPrivateAddress = Boolean.FALSE;

    @Column(name = "INFO")
    private String info;

    @Column(name = "FIRM_TITLE")
    private String firmTitle;

    @Column(name = "TAX_OFFICE")
    private String taxOffice;

    @Column(name = "TAX_NUMBER")
    private String taxNumber;

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

    @OneToMany(mappedBy = "parentOrganization", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Organization> childOrganizationList = new ArrayList<>();
}
