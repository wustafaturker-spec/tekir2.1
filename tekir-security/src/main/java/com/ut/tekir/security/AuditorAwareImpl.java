package com.ut.tekir.security;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Provides the current authenticated username for JPA auditing.
 * Used by @CreatedBy and @LastModifiedBy in AuditBase.
 */
@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("system");
        }
        String username = authentication.getName();
        // Truncate to 10 chars to match legacy column length
        if (username.length() > 10) {
            username = username.substring(0, 10);
        }
        return Optional.of(username);
    }
}
