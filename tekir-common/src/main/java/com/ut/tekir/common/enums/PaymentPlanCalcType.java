package com.ut.tekir.common.enums;

/**
 * Payment plan calculation type (ödeme planı hesaplama yöntemi).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum PaymentPlanCalcType {
    Rate,       // 0-Oran üzerinden
    Amount,     // 1-Tutar üzerinden
    Remaining   // 2-Kalan
}

