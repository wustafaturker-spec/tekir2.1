package com.ut.tekir.billing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT_RECORD", indexes = {
    @Index(name = "IDX_PAYMENT_TENANT", columnList = "TENANT_ID"),
    @Index(name = "IDX_PAYMENT_SUBSCRIPTION", columnList = "SUBSCRIPTION_ID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TENANT_ID", nullable = false)
    private Long tenantId;

    @Column(name = "SUBSCRIPTION_ID")
    private Long subscriptionId;

    @Column(name = "AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "CURRENCY", nullable = false, length = 3)
    @Builder.Default
    private String currency = "TRY";

    @Column(name = "STATUS", nullable = false, length = 20)
    @Builder.Default
    private String status = "PENDING";

    @Column(name = "EXTERNAL_PAYMENT_ID", length = 255)
    private String externalPaymentId;

    @Column(name = "PAYMENT_DATE")
    private LocalDateTime paymentDate;

    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
    }
}
