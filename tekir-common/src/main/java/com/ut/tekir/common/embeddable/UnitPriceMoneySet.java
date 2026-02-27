package com.ut.tekir.common.embeddable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UnitPriceMoneySet extends MoneySet {
    private static final long serialVersionUID = 1L;
    // Helper functionality or specific overrides if needed
}
