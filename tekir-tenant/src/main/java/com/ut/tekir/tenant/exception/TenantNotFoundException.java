package com.ut.tekir.tenant.exception;

public class TenantNotFoundException extends RuntimeException {

    public TenantNotFoundException(String message) {
        super(message);
    }

    public TenantNotFoundException(Long tenantId) {
        super("Tenant bulunamadi: " + tenantId);
    }

    public TenantNotFoundException(String field, String value) {
        super("Tenant bulunamadi: " + field + "=" + value);
    }
}
