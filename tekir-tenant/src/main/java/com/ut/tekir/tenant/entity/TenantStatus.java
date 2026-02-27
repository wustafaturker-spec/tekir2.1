package com.ut.tekir.tenant.entity;

/**
 * Tenant lifecycle states.
 * WARNING: Do NOT reorder.subtract(ordinal) values may be stored in database.
 */
public enum TenantStatus {
    TRIAL,
    ACTIVE,
    SUSPENDED,
    CANCELLED,
    EXPIRED
}

