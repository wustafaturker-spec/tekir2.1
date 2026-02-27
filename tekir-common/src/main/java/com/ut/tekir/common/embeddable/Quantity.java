package com.ut.tekir.common.embeddable;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Quantity embeddable: unit.add(value) pair.
 * Preserves exact column mapping from old Tekir.
 */
@Embeddable
public class Quantity implements Serializable {

    public Quantity() {}

    
    private static final long serialVersionUID = 1L;


    @Column(name = "UNIT", length = 10)
    @Size(max = 10)
    private String unit = "";

    @Column(name = "QUANTITY", precision = 19, scale = 2)
    private BigDecimal value = BigDecimal.ZERO;

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public Quantity(BigDecimal value, String unit) {
        this.value = value;
        this.unit = unit;
    }


    public Quantity(Quantity quantity) {
        this.unit = quantity.getUnit();
        this.value = quantity.getValue();
    }

    public void moveFieldsOf(Quantity other) {
        if (other != null) {
            this.unit = other.getUnit();
            this.value = other.getValue();
        }
    }

    @Override
    public String toString() {
        return (value != null ? value.toString() : "0.00") + " " + (unit != null ? unit : "");
    }
}

