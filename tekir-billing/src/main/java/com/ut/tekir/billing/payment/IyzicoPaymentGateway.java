package com.ut.tekir.billing.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * iyzico payment gateway implementation (Turkish payment processor).
 * TODO: Integrate with iyzico Java SDK when API keys are configured.
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "tekir.billing.payment-gateway", havingValue = "iyzico")
public class IyzicoPaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult createSubscription(Long tenantId, String planCode, String billingCycle) {
        log.info("iyzico: Creating subscription for tenant={}, plan={}, cycle={}",
                tenantId, planCode, billingCycle);
        // TODO: Implement iyzico subscription creation
        return PaymentResult.success("iyzico_sub_" + tenantId + "_" + System.currentTimeMillis());
    }

    @Override
    public PaymentResult cancelSubscription(String externalSubscriptionId) {
        log.info("iyzico: Cancelling subscription: {}", externalSubscriptionId);
        // TODO: Implement iyzico subscription cancellation
        return PaymentResult.success(externalSubscriptionId);
    }

    @Override
    public PaymentResult chargeOneTime(Long tenantId, BigDecimal amount, String currency, String description) {
        log.info("iyzico: One-time charge for tenant={}, amount={} {}", tenantId, amount, currency);
        // TODO: Implement iyzico payment
        return PaymentResult.success("iyzico_pay_" + System.currentTimeMillis());
    }
}

