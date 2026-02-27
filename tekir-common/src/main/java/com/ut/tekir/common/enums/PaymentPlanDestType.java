package com.ut.tekir.common.enums;

/**
 * Payment plan destination type (ödeme planı hedef seçimi).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum PaymentPlanDestType {
    Total,      // 0-Toplam tutar üzerinden
    Tax,        // 1-Vergi üzerinden
    Cost,       // 2-Masraf üzerinden
    Fee,        // 3-Harç üzerinden
    TaxBase     // 4-Vergi matrahı üzerinden
}

