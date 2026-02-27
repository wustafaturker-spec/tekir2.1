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

import com.ut.tekir.common.embeddable.Phone;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Contact phone entity with multiple phone type and usage flags.
 */
@Entity
@Table(name = "CONTACT_PHONE")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ContactPhone extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genericSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Contact owner;

    @Embedded
    @Valid
    private Phone phone = new Phone();

    @Column(name = "INFO")
    private String info;

    @Column(name = "ACTIVE_PHONE")
    private Boolean activePhone = Boolean.TRUE;

    @Column(name = "DEFAULT_PHONE")
    private Boolean defaultPhone = Boolean.FALSE;

    @Column(name = "HOME_PHONE")
    private Boolean homePhone = Boolean.FALSE;

    @Column(name = "WORK_PHONE")
    private Boolean workPhone = Boolean.FALSE;

    @Column(name = "OTHER_PHONE")
    private Boolean otherPhone = Boolean.FALSE;

    @Column(name = "FAX_PHONE")
    private Boolean faxPhone = Boolean.FALSE;

    @Column(name = "GSM_PHONE")
    private Boolean gsmPhone = Boolean.FALSE;

    @Column(name = "VEHICLE_PHONE")
    private Boolean vehiclePhone = Boolean.FALSE;

    @Column(name = "CALLER_PHONE")
    private Boolean callerPhone = Boolean.FALSE;

    @Column(name = "IMMOBILE_PHONE")
    private Boolean immobilePhone = Boolean.FALSE;

    @Column(name = "RELATED")
    private Boolean related = Boolean.FALSE;

    @Transient
    public String getUsageType() {
        if (immobilePhone) return "immobilePhone";
        if (gsmPhone) return "gsmPhone";
        if (vehiclePhone) return "vehiclePhone";
        if (callerPhone) return "callerPhone";
        if (faxPhone) return "faxPhone";
        return null;
    }

    @Transient
    public String getPhoneType() {
        if (homePhone) return "homePhone";
        if (workPhone) return "workPhone";
        if (otherPhone) return "otherPhone";
        if (related) return "relatedPhone";
        return null;
    }
}
