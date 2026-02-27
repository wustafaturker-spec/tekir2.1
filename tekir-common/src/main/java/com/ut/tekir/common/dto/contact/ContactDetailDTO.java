package com.ut.tekir.common.dto.contact;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ut.tekir.common.enums.AccountStatus;
import com.ut.tekir.common.enums.EntityType;
import com.ut.tekir.common.enums.InvoiceScenario;

public record ContactDetailDTO(
    Long id,
    String code,

    // Tek isim alanı
    String name,

    // Kimlik
    EntityType entityType,
    String taxNumber,
    String taxOffice,
    String ssn,
    String mersisNo,
    String customerCode,

    // Durum
    Boolean active,

    // Cari tipi flag'leri
    Boolean customerType,
    Boolean providerType,
    Boolean agentType,
    Boolean personnelType,
    Boolean branchType,
    Boolean bankType,

    // Organizasyon
    Long categoryId,
    String categoryName,
    Long organizationId,
    String organizationName,

    // Finansal
    String defaultCurrency,
    String accountingCode,
    Integer paymentTermDays,
    BigDecimal discountRate,
    String iban,
    BigDecimal creditLimit,
    BigDecimal riskBalance,

    // E-Fatura
    Boolean eInvoiceRegistered,
    String pkAlias,
    InvoiceScenario invoiceScenario,

    // Risk
    AccountStatus accountStatus,
    String blockReason,

    // CRM
    String sector,
    String customerSegment,
    String salesRepresentative,
    LocalDate firstContactDate,
    LocalDate lastContactDate,
    String referralSource,
    String info,

    // Alt listeler
    List<ContactAddressDTO> addresses,
    List<ContactPhoneDTO> phones,
    List<ContactNetworkDTO> networks,
    List<ContactOpportunityDTO> opportunities,
    List<ContactPersonEntryDTO> personEntries,

    // Audit
    LocalDateTime createDate,
    String createUser,
    LocalDateTime updateDate,
    String updateUser
) {}
