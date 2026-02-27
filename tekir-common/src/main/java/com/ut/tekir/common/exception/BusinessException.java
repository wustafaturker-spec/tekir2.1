package com.ut.tekir.common.exception;

import lombok.Getter;

/**
 * Business rule validation exception.
 * Replaces FacesMessages error pattern from legacy Seam code.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
        this.code = "BUSINESS_ERROR";
    }
}
