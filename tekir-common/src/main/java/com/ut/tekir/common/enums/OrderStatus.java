package com.ut.tekir.common.enums;

/**
 * Order status (sipariş durumları).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum OrderStatus {
    Open,       // 0-Açık
    Canceled,   // 1-İptal
    Rejected,   // 2-Reddedildi
    Approved,   // 3-Onaylandı
    Processing, // 4-İşleniyor
    Closed      // 5-Kapandı
}

