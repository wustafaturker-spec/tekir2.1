package com.ut.tekir.common.entity;

import com.ut.tekir.common.entity.DocumentBase;
import com.ut.tekir.common.enums.DocumentType;
import com.ut.tekir.common.enums.TradeAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "INVOICE")
@Getter
@Setter
public class Invoice extends DocumentBase {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;

        @Enumerated(EnumType.ORDINAL)
        @Column(name = "TRADE_ACTION")
        private TradeAction action;

        @ManyToOne
        @JoinColumn(name = "CONTACT_ID")
        private Contact contact;

        @ManyToOne
        @JoinColumn(name = "WAREHOUSE_ID")
        private Warehouse warehouse;

        @ManyToOne
        @JoinColumn(name = "ACCOUNT_ID")
        private Account account; // If closed invoice

        @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<InvoiceItem> items = new ArrayList<>();

        // --- Snapshot Fields for Reports ---

        @Embedded
        @Valid
        @AttributeOverrides({
                        @AttributeOverride(name = "street", column = @Column(name = "DELIVERY_ADDRESS_STREET")),
                        @AttributeOverride(name = "province", column = @Column(name = "DELIVERY_ADDRESS_PROVINCE")),
                        @AttributeOverride(name = "city", column = @Column(name = "DELIVERY_ADDRESS_CITY")),
                        @AttributeOverride(name = "country", column = @Column(name = "DELIVERY_ADDRESS_COUNTRY")),
                        @AttributeOverride(name = "zip", column = @Column(name = "DELIVERY_ADDRESS_ZIP"))
        })
        private com.ut.tekir.common.embeddable.Address deliveryAddress = new com.ut.tekir.common.embeddable.Address();

        @Embedded
        @Valid
        @AttributeOverrides({
                        @AttributeOverride(name = "value", column = @Column(name = "BEFORE_TAX_VAL")),
                        @AttributeOverride(name = "currency", column = @Column(name = "BEFORE_TAX_CCY")),
                        @AttributeOverride(name = "localAmount", column = @Column(name = "TOTAL_BEFORE_TAX_LCYVAL"))
        })
        private com.ut.tekir.common.embeddable.MoneySet beforeTax = new com.ut.tekir.common.embeddable.MoneySet();

        @Embedded
        @Valid
        @AttributeOverrides({
                        @AttributeOverride(name = "value", column = @Column(name = "TAX_TOTAL_VAL")),
                        @AttributeOverride(name = "currency", column = @Column(name = "TAX_TOTAL_CCY")),
                        @AttributeOverride(name = "localAmount", column = @Column(name = "TOTAL_TAX_LCYVAL"))
        })
        private com.ut.tekir.common.embeddable.MoneySet totalTax = new com.ut.tekir.common.embeddable.MoneySet();

        @Embedded
        @Valid
        @AttributeOverrides({
                        @AttributeOverride(name = "value", column = @Column(name = "GRAND_TOTAL_VAL")),
                        @AttributeOverride(name = "currency", column = @Column(name = "GRAND_TOTAL_CCY")),
                        @AttributeOverride(name = "localAmount", column = @Column(name = "GRAND_TOTAL_LCYVAL"))
        })
        private com.ut.tekir.common.embeddable.MoneySet grandTotal = new com.ut.tekir.common.embeddable.MoneySet();

        @Embedded
        @Valid
        @AttributeOverrides({
                        @AttributeOverride(name = "value", column = @Column(name = "TOTAL_FEE_VAL")),
                        @AttributeOverride(name = "currency", column = @Column(name = "TOTAL_FEE_CCY")),
                        @AttributeOverride(name = "localAmount", column = @Column(name = "TOTAL_FEE_LCYVAL"))
        })
        private com.ut.tekir.common.embeddable.MoneySet totalFee = new com.ut.tekir.common.embeddable.MoneySet();

        @Embedded
        @Valid
        @AttributeOverrides({
                        @AttributeOverride(name = "value", column = @Column(name = "TOTAL_EXPENSE_VAL")),
                        @AttributeOverride(name = "currency", column = @Column(name = "TOTAL_EXPENSE_CCY")),
                        @AttributeOverride(name = "localAmount", column = @Column(name = "TOTAL_EXPENSE_LCYVAL"))
        })
        private com.ut.tekir.common.embeddable.MoneySet totalExpense = new com.ut.tekir.common.embeddable.MoneySet();

        // --- e-Fatura Fields ---
        @Enumerated(EnumType.STRING)
        @Column(name = "EINVOICE_STATUS", length = 20)
        private com.ut.tekir.common.enums.EInvoiceStatus eInvoiceStatus = com.ut.tekir.common.enums.EInvoiceStatus.NONE;

        @Column(name = "EINVOICE_UUID", length = 36)
        private String eInvoiceUuid;

        @Enumerated(EnumType.STRING)
        @Column(name = "EINVOICE_SCENARIO", length = 20)
        private com.ut.tekir.common.enums.InvoiceScenario eInvoiceScenario;

        @Enumerated(EnumType.STRING)
        @Column(name = "EINVOICE_TYPE", length = 20)
        private com.ut.tekir.common.enums.EDocumentType eInvoiceType;

        // Legacy fields used in reports but logic might be different
        @Column(name = "TAX_EXCLUDED_TOTAL_LCYVAL")
        private java.math.BigDecimal taxExcludedTotalLcyVal; // KDV Matrah

        @Column(name = "TOTAL_DISCOUNT_LCYVAL")
        private java.math.BigDecimal totalDiscountLcyVal;

        @Column(name = "TOTAL_DOCUMENT_DISCOUNT_LCYVAL")
        private java.math.BigDecimal totalDocumentDiscountLcyVal;

        @Override
        public Long getId() {
                return id;
        }

        @Override
        public DocumentType getDocumentType() {
                return action == TradeAction.Sale
                                ? DocumentType.SaleInvoice
                                : DocumentType.PurchaseInvoice;
        }
}
