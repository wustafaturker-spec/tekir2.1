package com.ut.tekir.common.enums;

/**
 * Work bunch status (iş paketi durumu).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum WorkBunchStatus {
    Draft,      // 0-Planlanıyor
    Open,       // 1-Açık
    Close,      // 2-Kapalı
    Suspended,  // 3-Askıya alındı
    Canceled,   // 4-İptal edildi
    All         // 5
}

