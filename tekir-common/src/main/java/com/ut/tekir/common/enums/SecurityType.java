package com.ut.tekir.common.enums;

/**
 * Security type (yatırım aracı türü).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum SecurityType {
    All,            // 0
    DiscountBond,   // 1-İskontolu Tahvil
    CouponBond,     // 2-Kuponlu Tahvil
    IndexedBond,    // 3-Endeksli Tahvil
    EuroBond        // 4
}

