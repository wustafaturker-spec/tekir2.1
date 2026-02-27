package com.ut.tekir.tenant.resolver;

import com.ut.tekir.tenant.context.TenantContext;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Hibernate 6 tenant identifier resolver.
 * Provides the current tenant ID from TenantContext for @TenantId filtering.
 * Also registers itself as a HibernatePropertiesCustomizer to bind to Hibernate.
 */
@Component
public class CurrentTenantIdentifierResolverImpl
        implements CurrentTenantIdentifierResolver<String>, HibernatePropertiesCustomizer {

    private static final String DEFAULT_TENANT = "1";

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantContext.getTenantIdAsString();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}
