package com.ut.tekir.common.enums;

/**
 * Education type (eğitim durumu).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum EducationType {
    Unknown,        // 0-Bilinmeyen
    NotEducated,    // 1-Diplomasız
    Primary,        // 2-İlkokul
    Middle,         // 3-Ortaokul
    High,           // 4-Lise
    Vocational,     // 5-Meslek Yüksek Okul
    University,     // 6-Üniversite
    Master,         // 7-Yüksek Lisans
    Phd,            // 8-Doktora
    Other           // 9-Diğer
}

