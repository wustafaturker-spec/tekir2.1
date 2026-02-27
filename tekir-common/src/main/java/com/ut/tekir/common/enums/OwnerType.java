package com.ut.tekir.common.enums;

/**
 * File owner type (dosya sahiplik türü).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum OwnerType {
    All,        // 0-Bilinmiyor veya hepsi
    Contact,    // 1-Cari'ye eklenmiş dosya
    Vehicle     // 2-Araca eklenmiş dosya
}

