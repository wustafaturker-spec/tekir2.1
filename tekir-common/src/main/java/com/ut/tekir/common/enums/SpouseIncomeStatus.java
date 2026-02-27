package com.ut.tekir.common.enums;

/**
 * Spouse income status (eş gelir durumu).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum SpouseIncomeStatus {
    Unknown,        // 0
    Working,        // 1-Çalışıyor
    Notworking,     // 2-Çalışmıyor
    WithProfit,     // 3-Geliri olan
    WithNoProfit    // 4-Geliri olmayan
}

