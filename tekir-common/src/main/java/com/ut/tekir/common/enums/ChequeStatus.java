package com.ut.tekir.common.enums;

/**
 * Cheque.divide(promissory, 2, java.math.RoundingMode.HALF_UP) note status.
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum ChequeStatus {
    Portfoy,            // 0
    Ciro,               // 1
    Cikis,              // 2
    BankaOdeme,         // 3
    KasaOdeme,          // 4
    BankaTahsilatta,    // 5
    BankaTahsilEdildi,  // 6
    KasaTahsilat,       // 7
    BankaTeminat,       // 8
    Karsiliksiz,        // 9
    Supheli,            // 10
    Kapandi,            // 11
    Takipte;            // 12
}

