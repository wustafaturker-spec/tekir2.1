package com.ut.tekir.tenant.context;

/**
 * ThreadLocal holder for the current tenant ID.
 * Set by JwtAuthenticationFilter or TenantInterceptor, cleared in finally blocks.
 * CRITICAL: Always call clear() in a finally block to prevent memory leaks.
 */
public final class TenantContext {

    private static final ThreadLocal<Long> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContext() {
        // utility class
    }

    public static void setTenantId(Long tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static Long getTenantId() {
        return CURRENT_TENANT.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }

    /**
     * Returns the current tenant ID as String for Hibernate resolver.
     * Returns "1" (default tenant) if no tenant is set.
     */
    public static String getTenantIdAsString() {
        Long tenantId = CURRENT_TENANT.get();
        return tenantId != null ? tenantId.toString() : "1";
    }
}
