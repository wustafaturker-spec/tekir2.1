package com.ut.tekir.common.embeddable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

import com.ut.tekir.common.BaseConsts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "STREET", length = 250)
    @Size(max = 250)
    private String street;

    @Column(name = "PROVINCE", length = 40)
    @Size(max = 40)
    private String province;

    @Column(name = "CITY", length = 40)
    @Size(max = 40)
    private String city;

    @Column(name = "COUNTRY", length = 40)
    @Size(max = 40)
    private String country = BaseConsts.COUNTRY_NAME;

    @Column(name = "ZIP", length = 10)
    @Size(max = 10)
    private String zip;

    public String description() {
        StringBuilder sb = new StringBuilder();
        if (street != null && !street.isEmpty()) sb.append("\n").append(street);
        if (zip != null && !zip.isEmpty()) sb.append("\n").append(zip);
        if (province != null && !province.isEmpty()) sb.append("\n").append(province).append("/");
        if (city != null && !city.isEmpty()) sb.append(city);
        if (country != null && !country.isEmpty()) sb.append("\n-").append(country);
        return sb.toString();
    }
}
