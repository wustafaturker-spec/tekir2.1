package com.ut.tekir.common.embeddable;

import com.ut.tekir.common.entity.Tax;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
public class TaxEmbeddable {

    @ManyToOne
    @JoinColumn(name = "TAX_ID")
    private Tax tax;

    @Column(name = "RATE", precision = 19, scale = 2)
    private BigDecimal rate = BigDecimal.ZERO;
    
    @Column(name = "VAT_INCLUDED")
    private Boolean vatIncluded = Boolean.TRUE;

}
