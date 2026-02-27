package com.ut.tekir.common.enums;

/**
 * Tekir Document Type List.
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 * Must match legacy system exactly (81 values, ordinals 0-80).
 */
public enum DocumentType {
    Unknown,                            // 0
    DebitCreditVirement,                // 1
    FundTransfer,                       // 2
    PurchaseInvoice,                    // 3
    SaleInvoice,                        // 4
    PurchaseShipmentInvoice,            // 5  İrsaliyeli Alış Faturası
    SaleShipmentInvoice,                // 6
    Payment,                            // 7
    Collection,                         // 8
    PurchaseShipmentNote,               // 9
    SaleShipmentNote,                   // 10
    ProductTransfer,                    // 11
    ExpenseNote,                        // 12
    ProductVirement,                    // 13
    DebitCreditNote,                    // 14
    BankToBankTransfer,                 // 15
    BankToContactTransfer,              // 16
    BankToAccountTransfer,              // 17
    PorfolioToPortfolioTransfer,        // 18
    DepositAccount,                     // 19
    BondSale,                           // 20
    BondPurchase,                       // 21
    Repo,                               // 22
    ChequeStatusChanging,               // 23
    ChequePayment,                      // 24
    ChequeCollection,                   // 25
    ChequeBankPayment,                  // 26
    ChequeBankCollection,               // 27
    ChequeBankReturn,                   // 28
    ChequePaymentPayroll,               // 29
    ChequeCollectionPayroll,            // 30
    ChequeUnpaid,                       // 31
    ChequeDoubtful,                     // 32
    ChequeClosed,                       // 33
    PromissoryStatusChanging,           // 34
    PromissoryPayment,                  // 35
    PromissoryCollection,               // 36
    PromissoryBankPayment,              // 37
    PromissoryBankCollection,           // 38
    PromissoryBankReturn,               // 39
    PromissoryPaymentPayroll,           // 40
    PromissoryCollectionPayroll,        // 41
    PromissoryUnpaid,                   // 42
    PromissoryDoubtful,                 // 43
    PromissoryClosed,                   // 44
    InvestmentFundPurchase,             // 45
    InvestmentFundSale,                 // 46
    TransportInvoice,                   // 47
    TransportShipmentInvoice,           // 48
    ChequeTransfer,                     // 49
    PromissoryTransfer,                 // 50
    SpendingNote,                       // 51
    FixedAssetPurchaseInvoice,          // 52
    FixedAssetSaleInvoice,              // 53
    ChequeAccountPaymentPayroll,        // 54
    ChequeAccountCollectionPayroll,     // 55
    ChequeCollectedAtBankPayroll,       // 56
    PromissoryAccountPaymentPayroll,    // 57
    PromissoryAccountCollectionPayroll, // 58
    PromissoryCollectedAtBankPayroll,   // 59
    ChequeToBankAssurancePayroll,       // 60
    ChequeFromContactPayroll,           // 61
    PromissoryToBankAssurancePayroll,   // 62
    PromissoryFromContactPayroll,       // 63
    DebitCreditNotePayment,             // 64
    DebitCreditNoteCollection,          // 65
    ContactToBankTransfer,              // 66
    AccountToBankTransfer,              // 67
    ForeignExchange,                    // 68
    RetailSaleShipmentInvoice,          // 69
    SaleOrder,                          // 70
    PurchaseOrder,                      // 71
    ChequeBankPaymentPayroll,           // 72
    PromissoryBankPaymentPayroll,       // 73
    AccountCreditNote,                  // 74
    RetailSaleReturnInvoice,            // 75
    ReturnExpenseNote,                  // 76
    CountNote,                          // 77
    SaleProformaInvoice,                // 78
    PaymentItem,                        // 79
    CollectionItem;                     // 80

    /**
     * Resolve DocumentType from ordinal value.
     */
    public static DocumentType fromOrdinal(int anOrdinal) {
        for (DocumentType type : values()) {
            if (type.ordinal() == anOrdinal) {
                return type;
            }
        }
        return DocumentType.Unknown;
    }

    /**
     * Resolve DocumentType from name string.
     */
    public static DocumentType fromString(String documentName) {
        for (DocumentType type : values()) {
            if (type.name().equals(documentName)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Returns the camelCase version of the enum name.
     */
    public String camelCaseName() {
        StringBuilder sb = new StringBuilder(name());
        sb.replace(0, 1, name().substring(0, 1).toLowerCase());
        return sb.toString();
    }
}
