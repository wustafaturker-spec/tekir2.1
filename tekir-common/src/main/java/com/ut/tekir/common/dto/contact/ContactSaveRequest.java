package com.ut.tekir.common.dto.contact;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.ut.tekir.common.enums.AccountStatus;
import com.ut.tekir.common.enums.EntityType;
import com.ut.tekir.common.enums.InvoiceScenario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactSaveRequest(
    Long id,
    @NotBlank @Size(max = 20) String code,

    // Tek isim alanı: CORPORATE → "Ticari Unvan", INDIVIDUAL → "Ad Soyad"
    @Size(max = 255) String name,

    // Kimlik
    EntityType entityType,
    @Size(max = 20) String taxNumber,
    @Size(max = 20) String taxOffice,
    @Size(max = 20) String ssn,
    @Size(max = 16) String mersisNo,
    @Size(max = 50) String customerCode,

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
    Long organizationId,

    // Finansal
    @Size(min = 3, max = 3) String defaultCurrency,
    @Size(max = 50) String accountingCode,
    Integer paymentTermDays,
    BigDecimal discountRate,
    @Pattern(regexp = "^(TR\\d{24})?$", message = "IBAN TR ile başlamalı ve 26 karakter olmalıdır")
    @Size(max = 34) String iban,
    BigDecimal creditLimit,
    BigDecimal riskBalance,

    // E-Fatura
    Boolean eInvoiceRegistered,
    @Size(max = 255) String pkAlias,
    InvoiceScenario invoiceScenario,

    // Risk
    AccountStatus accountStatus,
    @Size(max = 500) String blockReason,

    // CRM
    @Size(max = 100) String sector,
    @Size(max = 50) String customerSegment,
    @Size(max = 100) String salesRepresentative,
    LocalDate firstContactDate,
    LocalDate lastContactDate,
    @Size(max = 100) String referralSource,
    String info,

    // Alt listeler (inline kayıt)
    List<ContactAddressSaveRequest> addresses,
    List<ContactPhoneSaveRequest> phones
) {}
