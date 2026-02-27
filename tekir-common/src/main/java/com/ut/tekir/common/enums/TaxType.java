package com.ut.tekir.common.enums;

/**
 * Tax type (vergi türü).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum TaxType {
    VAT,    // 0-KDV
    OTV,    // 1
    OIV,    // 2
    STOPAJ, // 3-Stopaj
    DAMGA,  // 4-Damga Vergisi
    TK      // 5
}

