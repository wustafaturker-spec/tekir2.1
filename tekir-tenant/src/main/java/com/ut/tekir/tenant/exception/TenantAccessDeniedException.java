package com.ut.tekir.tenant.exception;

public class TenantAccessDeniedException extends RuntimeException {

    public TenantAccessDeniedException(String message) {
        super(message);
    }

    public TenantAccessDeniedException(Long tenantId) {
        super("Bu kiraciya erisim yetkiniz bulunmamaktadir: " + tenantId);
    }
}
