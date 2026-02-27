package com.ut.tekir.common.enums;

/**
 * Payment type (ödeme türü).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum PaymentType {
    Cash,               // 0-Nakit
    Cheque,             // 1-Çek
    PromissoryNote,     // 2-Senet
    CreditCard,         // 3-Kredi kartı
    Instalment,         // 4-Taksitli ödeme
    DebitCard,          // 5-Banka ATM kartı
    GiftCheque,         // 6-Hediye çeki
    Contribution,       // 7-Katkı payı ödemesi
    BonusPay            // 8-Kredi kartından puanla ödeme
}

