package com.ut.tekir.billing.entity;

/**
 * Subscription lifecycle states.
 * WARNING: Do NOT reorder.subtract(ordinal) values may be stored in database.
 */
public enum SubscriptionStatus {
    TRIAL,
    ACTIVE,
    PAST_DUE,
    CANCELLED,
    EXPIRED
}

