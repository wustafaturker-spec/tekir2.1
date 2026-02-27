package com.ut.tekir.common.enums;

/**
 * Count note statuses (sayım fişi durumları).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum CountStatus {
    Open,       // 0-Açık
    Counting,   // 1-Sayılıyor
    Counted,    // 2-Sayıldı
    Editing,    // 3-Düzenlemeye açıldı
    Finished    // 4-Bitti
}

