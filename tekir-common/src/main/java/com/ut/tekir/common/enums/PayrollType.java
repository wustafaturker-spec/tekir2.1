package com.ut.tekir.common.enums;

public enum PayrollType {
    ClientEntry,     // Müşteriden Alınan
    VendorPayment,   // Satıcıya Verilen (Ciro)
    BankCollection,  // Bankaya Tahsile Verme
    BankCollateral,  // Bankaya Teminata Verme
    BankEntry,       // Bankadan İade/Giriş
    Collection,      // Elden Tahsilat (Kasa)
    Payment,         // Elden Ödeme
    StatusChange     // Durum Değişikliği
}

