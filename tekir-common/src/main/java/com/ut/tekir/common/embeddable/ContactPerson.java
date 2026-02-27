package com.ut.tekir.common.embeddable;

import java.io.Serializable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * Contact person embeddable with name, title, phone, and fax.
 * Preserves exact column mapping from old Tekir.
 */
@Embeddable
@Getter
@Setter
public class ContactPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "FIRST_NAME", length = 20)
    @Size(max = 20)
    private String firstName = "";

    @Column(name = "LAST_NAME", length = 30)
    @Size(max = 30)
    private String lastName = "";

    @Column(name = "TITLE", length = 30)
    @Size(max = 30)
    private String title = "";

    @Column(name = "EMAIL")
    private String email;

    @Embedded
    @Valid
    @AttributeOverrides({
        @AttributeOverride(name = "countryCode", column = @Column(name = "PHONE_COUNTRYCODE")),
        @AttributeOverride(name = "areaCode", column = @Column(name = "PHONE_AREACODE")),
        @AttributeOverride(name = "number", column = @Column(name = "PHONE_NUMBER")),
        @AttributeOverride(name = "extention", column = @Column(name = "PHONE_EXTENTION"))
    })
    private Phone phone = new Phone();

    @Embedded
    @Valid
    @AttributeOverrides({
        @AttributeOverride(name = "countryCode", column = @Column(name = "FAX_COUNTRYCODE")),
        @AttributeOverride(name = "areaCode", column = @Column(name = "FAX_AREACODE")),
        @AttributeOverride(name = "number", column = @Column(name = "FAX_NUMBER")),
        @AttributeOverride(name = "extention", column = @Column(name = "FAX_EXTENTION"))
    })
    private Phone fax = new Phone();
}
