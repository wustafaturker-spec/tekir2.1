package com.ut.tekir.common.enums;

/**
 * Return invoice status (iade fatura durumları).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum ReturnInvoiceStatus {
    Open,       // 0
    Processing, // 1
    Closed      // 2
}
