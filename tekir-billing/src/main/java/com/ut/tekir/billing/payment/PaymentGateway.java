package com.ut.tekir.billing.payment;

import java.math.BigDecimal;

/**
 * Payment gateway abstraction.
 * Implementations: Stripe, iyzico
 */
public interface PaymentGateway {

    PaymentResult createSubscription(Long tenantId, String planCode, String billingCycle);

    PaymentResult cancelSubscription(String externalSubscriptionId);

    PaymentResult chargeOneTime(Long tenantId, BigDecimal amount, String currency, String description);
}
