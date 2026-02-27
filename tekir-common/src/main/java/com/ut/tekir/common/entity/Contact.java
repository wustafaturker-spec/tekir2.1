package com.ut.tekir.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.ut.tekir.common.embeddable.DiscountOrExpense;
import com.ut.tekir.common.embeddable.MoneySet;
import com.ut.tekir.common.enums.AccountStatus;
import com.ut.tekir.common.enums.CurrencyRateType;
import com.ut.tekir.common.enums.EducationType;
import com.ut.tekir.common.enums.EntityType;
import com.ut.tekir.common.enums.GenderType;
import com.ut.tekir.common.enums.InvoiceScenario;
import com.ut.tekir.common.enums.MaritalStatus;
import com.ut.tekir.common.enums.SpouseIncomeStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Contact entity — Tekir ERP'nin merkezi cari hesap entity'si.
 * Müşteri, tedarikçi, acente, personel vb. temsil eder.
 *
 * Alan politikası:
 * - Aktif API alanları: name, code, entityType, taxNumber, ssn, finansal, CRM,
 * risk, e-fatura
 * - Legacy alanlar (DB'de tutulur, DTO'ya çıkmaz): midname, surname, fullname,
 * company,
 * legalName, displayName, person, debitLimit, riskLimit, hasDiscount,
 * hasWithholding,
 * discount, representative, exCode1, exCode2, title
 */
@Entity
@Table(name = "CONTACT")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Contact extends AuditBase implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;

        @Column(name = "CODE", nullable = false, unique = true, length = 20)
        @NotNull
        @Size(min = 1, max = 20)
        private String code;

        // Tek isim alanı: CORPORATE için "Ticari Unvan", INDIVIDUAL için "Ad Soyad"
        @Column(name = "NAME", length = 255)
        @Size(max = 255)
        private String name;

        @Column(name = "MIDNAME", length = 30)
        @Size(max = 30)
        private String midname;

        @Column(name = "SURNAME", length = 30)
        @Size(max = 30)
        private String surname;

        @Column(name = "FULLNAME", length = 92)
        @Size(max = 92)
        private String fullname;

        @Column(name = "INFO")
        private String info;

        @Column(name = "OPEN_DATE")
        private LocalDate openDate = LocalDate.now();

        @Column(name = "ISACTIVE")
        private Boolean active = Boolean.TRUE;

        // Type flags
        @Column(name = "ALL_TYPE")
        private Boolean allType = Boolean.FALSE;

        @Column(name = "CUSTOMER_TYPE")
        private Boolean customerType = Boolean.FALSE;

        @Column(name = "PROVIDER_TYPE")
        private Boolean providerType = Boolean.FALSE;

        @Column(name = "AGENT_TYPE")
        private Boolean agentType = Boolean.FALSE;

        @Column(name = "PERSONNEL_TYPE")
        private Boolean personnelType = Boolean.FALSE;

        @Column(name = "BRANCH_TYPE")
        private Boolean branchType = Boolean.FALSE;

        @Column(name = "CONTACT_TYPE")
        private Boolean contactType = Boolean.FALSE;

        @Column(name = "BANK_TYPE")
        private Boolean bankType = Boolean.FALSE;

        @Column(name = "FOUNDATION_TYPE")
        private Boolean foundationType = Boolean.FALSE;

        @Column(name = "RELATED_TYPE")
        private Boolean relatedType = Boolean.FALSE;

        @ManyToOne
        @JoinColumn(name = "CONTACT_CATEGORY_ID")
        private ContactCategory category;

        @Column(name = "`SYSTEM`")
        private Boolean system;

        @Column(name = "PERSON")
        private Boolean person = Boolean.FALSE;

        // --- Kimlik (Identity) ---
        @Column(name = "CUSTOMER_CODE", length = 50)
        @Size(max = 50)
        private String customerCode;

        @Column(name = "LEGAL_NAME", length = 255)
        @Size(max = 255)
        private String legalName;

        @Column(name = "DISPLAY_NAME", length = 255)
        @Size(max = 255)
        private String displayName;

        @Column(name = "ENTITY_TYPE", length = 20)
        @Enumerated(EnumType.STRING)
        private EntityType entityType = EntityType.CORPORATE;

        @Column(name = "MERSIS_NO", length = 16)
        @Size(max = 16)
        private String mersisNo;

        // --- Finansal (Financial) ---
        @Column(name = "DEFAULT_CURRENCY", length = 3)
        @Size(max = 3)
        private String defaultCurrency = "TRY";

        @Column(name = "ACCOUNTING_CODE", length = 50)
        @Size(max = 50)
        private String accountingCode;

        @Column(name = "PAYMENT_TERM_DAYS")
        private Integer paymentTermDays = 30;

        @Column(name = "DISCOUNT_RATE_PERCENT", precision = 5, scale = 2)
        private BigDecimal discountRate = BigDecimal.ZERO;

        @Column(name = "IBAN", length = 34)
        @Size(max = 34)
        private String iban;

        // --- E-Fatura (E-Invoice) ---
        @Column(name = "E_INVOICE_REGISTERED")
        private Boolean eInvoiceRegistered = Boolean.FALSE;

        @Column(name = "PK_ALIAS", length = 255)
        @Size(max = 255)
        private String pkAlias;

        @Column(name = "INVOICE_SCENARIO", length = 20)
        @Enumerated(EnumType.STRING)
        private InvoiceScenario invoiceScenario;

        @Column(name = "EINVOICE_CHECKED_AT")
        private java.time.LocalDateTime eInvoiceCheckedAt;

        @Column(name = "EINVOICE_ALIAS_LIST", columnDefinition = "TEXT")
        private String eInvoiceAliasList;

        @Column(name = "GB_ALIAS", length = 255)
        @Size(max = 255)
        private String gbAlias;

        // --- Risk Yönetimi (Risk Management) ---
        @Column(name = "CREDIT_LIMIT", precision = 18, scale = 2)
        private BigDecimal creditLimit = BigDecimal.ZERO;

        @Column(name = "RISK_BALANCE", precision = 18, scale = 2)
        private BigDecimal riskBalance = BigDecimal.ZERO;

        @Column(name = "ACCOUNT_STATUS", length = 20)
        @Enumerated(EnumType.STRING)
        private AccountStatus accountStatus = AccountStatus.ACTIVE;

        @Column(name = "BLOCK_REASON", length = 500)
        @Size(max = 500)
        private String blockReason;

        // --- Sektör & CRM ---
        @Column(name = "SECTOR", length = 100)
        @Size(max = 100)
        private String sector;

        @Column(name = "CUSTOMER_SEGMENT", length = 50)
        @Size(max = 50)
        private String customerSegment;

        @Column(name = "SALES_REPRESENTATIVE", length = 100)
        @Size(max = 100)
        private String salesRepresentative;

        @Column(name = "FIRST_CONTACT_DATE")
        private LocalDate firstContactDate;

        @Column(name = "LAST_CONTACT_DATE")
        private LocalDate lastContactDate;

        @Column(name = "REFERRAL_SOURCE", length = 100)
        @Size(max = 100)
        private String referralSource;

        @Column(name = "REPRESENTATIVE", length = 30)
        @Size(max = 30)
        private String representative;

        @Column(name = "COMPANY", length = 250)
        @Size(max = 250)
        private String company;

        @Column(name = "TITLE", length = 30)
        @Size(max = 30)
        private String title;

        @Column(name = "TAX_NUMBER", length = 20)
        @Size(max = 20)
        private String taxNumber;

        @Column(name = "TAX_OFFICE", length = 20)
        @Size(max = 20)
        private String taxOffice;

        @Column(name = "SSN", length = 20)
        @Size(max = 20)
        private String ssn;

        @Column(name = "EXCODE1", length = 10)
        @Size(max = 10)
        private String exCode1;

        @Column(name = "EXCODE2", length = 10)
        @Size(max = 10)
        private String exCode2;

        @Column(name = "DEBIT_LIMIT", precision = 10, scale = 2)
        private BigDecimal debitLimit = BigDecimal.ZERO;

        @Column(name = "RISK_LIMIT", precision = 10, scale = 2)
        private BigDecimal riskLimit = BigDecimal.ZERO;

        @ManyToOne
        @JoinColumn(name = "ORGANIZATION_ID")
        private Organization organization;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "OWN_COMPANY_ID")
        private Contact ownCompany;

        @Column(name = "START_WORKING_DATE")
        private LocalDate startWorkingDate;

        @Column(name = "END_WORKING_DATE")
        private LocalDate endWorkingDate;

        @Column(name = "ISACTIVE_PERSONEL")
        private Boolean activePersonel = Boolean.TRUE;

        @Column(name = "SGK_NUMBER", length = 20)
        @Size(max = 20)
        private String sgkNumber;

        @Column(name = "BIRTH_DATE")
        private LocalDate birthdate;

        @Column(name = "BLOOD_GROUP", length = 6)
        @Size(max = 6)
        private String bloodGroup;

        @Column(name = "FATHER_NAME", length = 30)
        @Size(max = 30)
        private String fatherName;

        @Column(name = "gender")
        @Enumerated(EnumType.ORDINAL)
        private GenderType gender;

        @Column(name = "education")
        @Enumerated(EnumType.ORDINAL)
        private EducationType education;

        @Column(name = "PASSPORT_NUMBER")
        private String passportNo;

        @Column(name = "MARITAL_STATUS")
        @Enumerated(EnumType.ORDINAL)
        private MaritalStatus maritalStatus;

        @Column(name = "SPOUSE_INCOME_STAT")
        @Enumerated(EnumType.ORDINAL)
        private SpouseIncomeStatus spouseIncomeStatus;

        @Column(name = "CHILD_NUMBER")
        private Integer childNumber;

        @Column(name = "DEPENDENTS")
        private Integer dependents;

        @Embedded
        @Valid
        @AttributeOverrides({
                        @AttributeOverride(name = "localAmount", column = @Column(name = "LCYVAL")),
                        @AttributeOverride(name = "currency", column = @Column(name = "CCY")),
                        @AttributeOverride(name = "value", column = @Column(name = "CCYVAL"))
        })
        private MoneySet grossSalary = new MoneySet();

        @Column(name = "HAS_WITHHOLDING")
        private Boolean hasWithholding = Boolean.FALSE;

        @Column(name = "IS_PUBLIC")
        private Boolean isPublic = Boolean.FALSE;

        @Embedded
        @Valid
        @AttributeOverrides({
                        @AttributeOverride(name = "percentage", column = @Column(name = "DISCOUNT_PERCENTAGE")),
                        @AttributeOverride(name = "rate", column = @Column(name = "DISCOUNT_RATE")),
                        @AttributeOverride(name = "currency", column = @Column(name = "DISCOUNT_CCY")),
                        @AttributeOverride(name = "value", column = @Column(name = "DISCOUNT_VALUE")),
                        @AttributeOverride(name = "localAmount", column = @Column(name = "DISCOUNT_LCYVAL"))
        })
        private DiscountOrExpense discount = new DiscountOrExpense();

        @Column(name = "HAS_DISCOUNT")
        private Boolean hasDiscount = Boolean.FALSE;

        @Column(name = "PAYMENT_PLAN_ID")
        private Long paymentPlanId;

        @Enumerated(EnumType.ORDINAL)
        @Column(name = "CURRENCY_RATE_TYPE")
        private CurrencyRateType currencyRateType = CurrencyRateType.Ask;

        // OneToMany relationships
        @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
        @OrderBy("defaultAddress DESC, activeAddress DESC")
        private List<ContactAddress> addressList = new ArrayList<>();

        @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
        @OrderBy("defaultPhone DESC, activePhone DESC")
        private List<ContactPhone> phoneList = new ArrayList<>();

        @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
        @OrderBy("defaultNetwork DESC, activeNetwork DESC")
        private List<ContactNetwork> networkList = new ArrayList<>();

        @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<ContactBankAccount> bankAccountList = new ArrayList<>();

        @OneToMany(mappedBy = "ownCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
        private List<Contact> personelList = new ArrayList<>();

        @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        @OrderBy("stage ASC, expectedCloseDate ASC")
        private List<ContactOpportunity> opportunityList = new ArrayList<>();

        @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        @OrderBy("isDefault DESC")
        private List<ContactPersonEntry> personEntryList = new ArrayList<>();
}
