package com.ut.tekir.common.enums;

/**
 * Bank transfer types.
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum BankTransferType {
    Unknown,    // 0
    Virman,     // 1-Virman
    Havale,     // 2-Havale
    Eft,        // 3-EFT
    Swift,      // 4
    Vezne       // 5-Banka veznesinden direkt yapılan işlemler
}

