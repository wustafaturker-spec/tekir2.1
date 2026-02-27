package com.ut.tekir.security;

import com.ut.tekir.tenant.context.TenantContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetailsService implementation that loads users from the existing USERS
 * table.
 * Supports both legacy MD5 and new BCrypt password hashes.
 * Tenant.subtract(aware): filters by TENANT_ID when available.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TekirUserDetailsService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String tenantId = TenantContext.getTenantIdAsString();

        // Query USERS table with tenant filter
        String userQuery = "SELECT u.ID, u.USER_NAME, u.PASSWORD, u.ISACTIVE, u.TENANT_ID " +
                "FROM USERS u WHERE u.USER_NAME = :username";

        // Apply tenant filter if not loading for a system-level operation
        if (tenantId != null && !"1".equals(tenantId)) {
            userQuery += " AND u.TENANT_ID = :tenantId";
        }

        var query = entityManager.createNativeQuery(userQuery)
                .setParameter("username", username);

        if (tenantId != null && !"1".equals(tenantId)) {
            query.setParameter("tenantId", tenantId);
        }

        List<Object[]> results = query.getResultList();

        if (results.isEmpty()) {
            // Fallback: try without tenant filter for backward compatibility
            results = entityManager.createNativeQuery(
                    "SELECT u.ID, u.USER_NAME, u.PASSWORD, u.ISACTIVE, u.TENANT_ID " +
                            "FROM USERS u WHERE u.USER_NAME = :username")
                    .setParameter("username", username)
                    .getResultList();
        }

        if (results.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        Object[] row = results.get(0);
        Long userId = ((Number) row[0]).longValue();
        String userName = (String) row[1];
        String password = (String) row[2];
        boolean active = row[3] != null && (Boolean) row[3];
        String userTenantId = row[4] != null ? row[4].toString() : "1";
        Long userTenantIdLong = Long.parseLong(userTenantId);

        // Load roles from USER_ROLE join table
        List<String> roleResults = entityManager.createNativeQuery(
                "SELECT r.ROLE_NAME FROM USER_ROLE ur " +
                        "JOIN ROLE r ON ur.ROLE_ID = r.ID " +
                        "WHERE ur.USER_ID = :userId")
                .setParameter("userId", userId)
                .getResultList();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String roleName : roleResults) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
        }
        log.info("User {} roles: {}", username, roleResults);

        // Load permissions if PERMISSION table exists
        try {
            List<String> permResults = entityManager.createNativeQuery(
                    "SELECT p.PERMISSION_CODE FROM ROLE_PERMISSION rp " +
                            "JOIN PERMISSION p ON rp.PERMISSION_ID = p.ID " +
                            "JOIN USER_ROLE ur ON ur.ROLE_ID = rp.ROLE_ID " +
                            "WHERE ur.USER_ID = :userId")
                    .setParameter("userId", userId)
                    .getResultList();

            for (String permissionCode : permResults) {
                authorities.add(new SimpleGrantedAuthority(permissionCode));
            }
            log.info("User {} permissions loaded: {}", username, permResults);
        } catch (Exception e) {
            // PERMISSION table may not exist yet-skip
            log.warn("Permissions loading FAILED for user {}: {}", username, e.getMessage(), e);
        }
        log.info("User {} total authorities: {}", username, authorities);

        return new TekirUserDetails(
                userName,
                password,
                active,
                authorities,
                userTenantIdLong,
                userId);
    }
}
