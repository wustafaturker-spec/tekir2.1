package com.ut.tekir.common.enums;

/**
 * Document file type (dosya türü).
 * WARNING: Ordinal values are stored in database. NEVER change the order.
 */
public enum DocumentFileType {
    CriminalRecords,    // 0-Adli Sicil
    Diploma,            // 1
    Note,               // 2-Dekont, Makbuz
    DrivingLicense,     // 3-Ehliyet
    Photo,              // 4-Fotoğraf
    AddressIssue,       // 5-İkametgah
    SingIssue,          // 6-İmza Sirküleri
    RentContract,       // 7-Kira kontratı
    IdCard,             // 8-Nüfus Cüzdanı
    IdIssue,            // 9-NC Sureti
    Report,             // 10-Rapor
    Certificate,        // 11-Sertifika
    SSNIssue,           // 12-SGK Belgesi
    TradeIssue,         // 13-Sicil Gazetesi
    Policy,             // 14-Sig. Poliçesi
    Contract,           // 15-Sözleşme
    Deed,               // 16-Tapu Senedi
    VehicleCard,        // 17-Taşıt Kartı
    SubsIssue,          // 18-Vekalet
    AuthIssue,          // 19-Yetki Belgesi
    TaxIssue,           // 20-Vergi Levhası
    TaxPayerIssue,      // 21-Mükellefiyet Yazısı
    OperationIssue,     // 22-Faaliyet Belgesi
    VehicleLicense      // 23-Ruhsat
}

