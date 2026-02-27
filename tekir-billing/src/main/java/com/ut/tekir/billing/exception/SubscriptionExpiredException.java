package com.ut.tekir.billing.exception;

public class SubscriptionExpiredException extends RuntimeException {

    public SubscriptionExpiredException(String message) {
        super(message);
    }
}
