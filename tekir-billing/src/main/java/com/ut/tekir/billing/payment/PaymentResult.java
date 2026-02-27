package com.ut.tekir.billing.payment;

public record PaymentResult(
    boolean success,
    String externalPaymentId,
    String message
) {
    public static PaymentResult success(String externalPaymentId) {
        return new PaymentResult(true, externalPaymentId, null);
    }

    public static PaymentResult failure(String message) {
        return new PaymentResult(false, null, message);
    }
}
