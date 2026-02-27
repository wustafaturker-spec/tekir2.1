package com.ut.tekir.billing.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Stripe payment gateway implementation.
 * TODO: Integrate with Stripe Java SDK when API keys are configured.
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "tekir.billing.payment-gateway", havingValue = "stripe", matchIfMissing = true)
public class StripePaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult createSubscription(Long tenantId, String planCode, String billingCycle) {
        log.info("Stripe: Creating subscription for tenant={}, plan={}, cycle={}",
                tenantId, planCode, billingCycle);
        // TODO: Implement Stripe Subscription creation
        return PaymentResult.success("stripe_sub_" + tenantId + "_" + System.currentTimeMillis());
    }

    @Override
    public PaymentResult cancelSubscription(String externalSubscriptionId) {
        log.info("Stripe: Cancelling subscription: {}", externalSubscriptionId);
        // TODO: Implement Stripe Subscription cancellation
        return PaymentResult.success(externalSubscriptionId);
    }

    @Override
    public PaymentResult chargeOneTime(Long tenantId, BigDecimal amount, String currency, String description) {
        log.info("Stripe: One-time charge for tenant={}, amount={} {}", tenantId, amount, currency);
        // TODO: Implement Stripe PaymentIntent
        return PaymentResult.success("stripe_pi_" + System.currentTimeMillis());
    }
}

