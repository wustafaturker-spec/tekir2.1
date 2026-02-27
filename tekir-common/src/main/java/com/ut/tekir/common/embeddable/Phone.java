package com.ut.tekir.common.embeddable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
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
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "COUNTRY_CODE", length = 3)
    @Size(max = 3)
    private String countryCode = BaseConsts.COUNTRY_CODE;

    @Column(name = "AREA_CODE", length = 6)
    @Size(max = 6)
    private String areaCode;

    @Column(name = "PHONE_NUMBER", length = 15)
    @Size(max = 15)
    private String number;

    @Column(name = "EXTENTION", length = 5)
    @Size(max = 5)
    private String extention;

    @Transient
    public String getFullNumber() {
        StringBuilder sb = new StringBuilder();
        if (areaCode != null && !areaCode.isEmpty()) sb.append("(").append(areaCode).append(") ");
        if (number != null && !number.isEmpty() && number.length() >= 7) {
            sb.append(number.substring(0, 3)).append(" ")
              .append(number.substring(3, 5)).append(" ")
              .append(number.substring(5, 7));
        }
        if (extention != null && !extention.isEmpty()) sb.append(" #").append(extention);
        return sb.toString();
    }
}
