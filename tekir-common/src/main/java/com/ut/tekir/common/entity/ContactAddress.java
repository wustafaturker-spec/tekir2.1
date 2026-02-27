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
import jakarta.persistence.Transient;
import jakarta.validation.Valid;

import com.ut.tekir.common.embeddable.Address;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Contact address entity with multiple address type flags.
 * Preserves CONTACT_ADDRESS table and all flag columns.
 */
@Entity
@Table(name = "CONTACT_ADDRESS")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ContactAddress extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Contact owner;

    @Column(name = "INFO")
    private String info;

    @Embedded
    @Valid
    private Address address = new Address();

    @Column(name = "ACTIVE_ADDRESS")
    private Boolean activeAddress = Boolean.TRUE;

    @Column(name = "DEFAULT_ADDRESS")
    private Boolean defaultAddress = Boolean.FALSE;

    @Column(name = "INVOICE_ADDRESS")
    private Boolean invoiceAddress = Boolean.FALSE;

    @Column(name = "SHIPPING_ADDRESS")
    private Boolean shippingAddress = Boolean.FALSE;

    @Column(name = "DELIVERY_ADDRESS")
    private Boolean deliveryAddress = Boolean.FALSE;

    @Column(name = "HOME_ADDRESS")
    private Boolean homeAddress = Boolean.FALSE;

    @Column(name = "WORK_ADDRESS")
    private Boolean workAddress = Boolean.FALSE;

    @Column(name = "HOMEOFFICE_ADDRESS")
    private Boolean homeOfficeAddress = Boolean.FALSE;

    @Column(name = "OTHER_ADDRESS")
    private Boolean otherAddress = Boolean.FALSE;

    @Column(name = "RELATED")
    private Boolean related = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    @ManyToOne
    @JoinColumn(name = "PROVINCE_ID")
    private Province province;

    @Column(name = "USED")
    private Boolean used = Boolean.FALSE;

    @Column(name = "RECIPIENT_NAME", length = 30)
    private String recipientName;

    @Column(name = "RECIPIENT_SURNAME", length = 30)
    private String recipientSurName;

    @Transient
    public String getUsageType() {
        if (homeAddress) return "homeAddress";
        if (workAddress) return "workAddress";
        if (otherAddress) return "otherAddress";
        if (homeOfficeAddress) return "homeOfficeAddress";
        if (related) return "relatedAddress";
        return null;
    }

    public void setUsageType(String usageType) {
        homeAddress = Boolean.FALSE;
        homeOfficeAddress = Boolean.FALSE;
        workAddress = Boolean.FALSE;
        otherAddress = Boolean.FALSE;
        related = Boolean.FALSE;

        if ("homeAddress".equals(usageType)) homeAddress = Boolean.TRUE;
        else if ("homeOfficeAddress".equals(usageType)) homeOfficeAddress = Boolean.TRUE;
        else if ("workAddress".equals(usageType)) workAddress = Boolean.TRUE;
        else if ("otherAddress".equals(usageType)) otherAddress = Boolean.TRUE;
        else if ("relatedAddress".equals(usageType)) related = Boolean.TRUE;
    }
}
