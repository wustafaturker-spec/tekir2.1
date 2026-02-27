package com.ut.tekir.common.enums;

/**
 * Customs gate type (sınır kapısı türü).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum CustomsGateType {
    Unknown,    // 0-Bilinmeyen ve Diğer
    Land,       // 1-Kara Sınır Kapısı
    Air,        // 2-Hava Sınır Kapısı
    Sea,        // 3-Deniz Sınır Kapısı
    FreeZone,   // 4-Serbest Bölge
    Railway,    // 5-Demiryolu Sınır Kapısı
    PipeLine,   // 6-Boru Hattı
    Internal    // 7-İç gümrük İdaresi
}

