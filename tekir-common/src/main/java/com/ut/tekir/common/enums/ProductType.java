package com.ut.tekir.common.enums;

/**
 * Product type classification.
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum ProductType {
    Unknown,           // 0
    Product,           // 1
    Service,           // 2
    Expense,           // 3
    Discount,          // 4
    DocumentExpense,   // 5
    DocumentDiscount,  // 6
    Fee,               // 7
    DocumentFee,       // 8
    ContactDiscount,   // 9
    DiscountAddition,  // 10
    ExpenseAddition;   // 11
}
