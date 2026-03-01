package com.ut.tekir.api.service;

import com.ut.tekir.security.JwtService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AuthService — roadmap §4.4 şifre geçiş stratejisi.
 * Login sırasında MD5 hash'leri otomatik BCrypt'e yükseltir.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final com.ut.tekir.repository.UserRepository userRepository;
    private final com.ut.tekir.repository.PasswordResetTokenRepository passwordResetTokenRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

    @Transactional(readOnly = true)
    public List<TenantInfoDTO> getTenantsForEmail(String email) {
        String sql = """
            SELECT t.ID, t.NAME, t.SLUG, t.LOGO_URL
            FROM USERS u
            JOIN TENANT t ON CAST(u.TENANT_ID AS BIGINT) = t.ID
            WHERE LOWER(u.EMAIL) = LOWER(:email)
              AND u.ISACTIVE = true
              AND t.ACTIVE = true
            ORDER BY t.NAME
            """;
        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(sql)
            .setParameter("email", email)
            .getResultList();
        return rows.stream().map(r -> new TenantInfoDTO(
            ((Number) r[0]).longValue(),
            (String) r[1],
            (String) r[2],
            (String) r[3]
        )).toList();
    }

    @Transactional
    public AuthResponse login(String loginInput, String password, Long providedTenantId) {
        // 1. Resolve Tenant — use provided tenantId if supplied, otherwise auto-detect
        Long tenantId;
        if (providedTenantId != null) {
            tenantId = providedTenantId;
        } else {
            tenantId = resolveTenantId(loginInput);
        }

        if (tenantId != null) {
            com.ut.tekir.tenant.context.TenantContext.setTenantId(tenantId);
        }

        // 2. Authenticate via Spring Security
        // Note: We use the input as the "username" for authentication.
        // If it's an email, we need to ensure loadUserByUsername handles it or we
        // resolve the actual username.
        // For now, let's assume username=email for new users or we resolve the username
        // from the found user.
        String usernameToUse = resolveUsername(loginInput);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usernameToUse, password));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Auto-upgrade MD5 → BCrypt (§4.4)
        upgradePasswordIfNeeded(usernameToUse, password);

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return new AuthResponse(accessToken, refreshToken, "Bearer", tenantId != null ? tenantId.toString() : "1");
    }

    private Long resolveTenantId(String loginInput) {
        try {
            List<Object> results = entityManager.createNativeQuery(
                    "SELECT TENANT_ID FROM USERS WHERE LOWER(EMAIL) = LOWER(:email) LIMIT 1")
                    .setParameter("email", loginInput)
                    .getResultList();

            if (!results.isEmpty() && results.get(0) != null) {
                Object tidObj = results.get(0);
                if (tidObj instanceof Number) {
                    return ((Number) tidObj).longValue();
                } else if (tidObj instanceof String) {
                    try {
                        return Long.parseLong((String) tidObj);
                    } catch (NumberFormatException nfe) {
                        log.warn("Could not parse tenant ID from string: {}", tidObj);
                    }
                }
                return null;
            }
        } catch (Exception e) {
            log.warn("Failed to resolve tenant for user {}: {}", loginInput, e.getMessage());
        }
        return null;
    }

    private String resolveUsername(String email) {
        Long tenantId = com.ut.tekir.tenant.context.TenantContext.getTenantId();
        try {
            jakarta.persistence.Query nq;
            if (tenantId != null) {
                // Filter by tenant so we pick the right user when multiple tenants share an email
                nq = entityManager.createNativeQuery(
                        "SELECT USER_NAME FROM USERS WHERE LOWER(EMAIL) = LOWER(:email) " +
                        "AND CAST(TENANT_ID AS BIGINT) = :tenantId LIMIT 1")
                        .setParameter("email", email)
                        .setParameter("tenantId", tenantId);
            } else {
                nq = entityManager.createNativeQuery(
                        "SELECT USER_NAME FROM USERS WHERE LOWER(EMAIL) = LOWER(:email) ORDER BY CAST(TENANT_ID AS BIGINT) ASC LIMIT 1")
                        .setParameter("email", email);
            }
            @SuppressWarnings("unchecked")
            List<Object> results = nq.getResultList();
            if (!results.isEmpty() && results.get(0) != null) {
                return (String) results.get(0);
            }
        } catch (Exception e) {
            // ignore
        }
        return email;
    }

    public AuthResponse refresh(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);

        // Restore tenant context from refresh token claims
        Long tenantId = jwtService.extractTenantId(refreshToken);
        if (tenantId != null) {
            com.ut.tekir.tenant.context.TenantContext.setTenantId(tenantId);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new BadCredentialsException("Geçersiz veya süresi dolmuş refresh token");
        }

        String newAccessToken = jwtService.generateToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        String tid = userDetails instanceof com.ut.tekir.security.TekirUserDetails
                ? ((com.ut.tekir.security.TekirUserDetails) userDetails).getTenantId().toString()
                : "1";

        return new AuthResponse(newAccessToken, newRefreshToken, "Bearer", tid);
    }

    /**
     * If password is stored as plain MD5 (32 chars, no prefix), upgrade to BCrypt.
     */
    @SuppressWarnings("unchecked")
    private void upgradePasswordIfNeeded(String username, String rawPassword) {
        try {
            var results = entityManager.createNativeQuery(
                    "SELECT ID, PASSWORD FROM USERS WHERE USER_NAME = :username")
                    .setParameter("username", username)
                    .getResultList();

            if (results.isEmpty())
                return;

            Object[] row = (Object[]) results.get(0);
            String storedHash = (String) row[1];

            // MD5 hashes are exactly 32 hex chars and don't start with "{" prefix
            if (storedHash != null && storedHash.length() == 32 && !storedHash.startsWith("{")) {
                String bcryptHash = "{bcrypt}" + bCryptEncoder.encode(rawPassword);
                entityManager.createNativeQuery(
                        "UPDATE USERS SET PASSWORD = :newHash WHERE USER_NAME = :username")
                        .setParameter("newHash", bcryptHash)
                        .setParameter("username", username)
                        .executeUpdate();
                log.info("Password upgraded from MD5 to BCrypt for user: {}", username);
            }
        } catch (Exception e) {
            log.warn("Password upgrade failed for user {}: {}", username, e.getMessage());
        }
    }

    @Transactional
    public void forgotPassword(String email) {
        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            log.warn("Password reset requested for non-existent email: {}", email);
            return; // Fail silently for security
        }

        var user = userOptional.get();
        // Check for existing token
        var existingToken = passwordResetTokenRepository.findByUser(user);
        existingToken.ifPresent(t -> { passwordResetTokenRepository.delete(t); passwordResetTokenRepository.flush(); });

        // Create new token
        String token = java.util.UUID.randomUUID().toString();
        var resetToken = new com.ut.tekir.common.entity.PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(java.time.LocalDateTime.now().plusHours(24));
        passwordResetTokenRepository.save(resetToken);

        // SIMULATE EMAIL SENDING
        log.info("==================================================");
        log.info("PASSWORD RESET TOKEN FOR {}: {}", email, token);
        log.info("Link: http://localhost:3000/reset-password?token={}", token);
        log.info("==================================================");
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        var resetTokenOptional = passwordResetTokenRepository.findByToken(token);
        if (resetTokenOptional.isEmpty()) {
            throw new com.ut.tekir.common.exception.BusinessException("invalid_token",
                    "Geçersiz veya süresi dolmuş token.");
        }

        var resetToken = resetTokenOptional.get();
        if (resetToken.isExpired()) {
            passwordResetTokenRepository.delete(resetToken);
            throw new com.ut.tekir.common.exception.BusinessException("token_expired", "Token süresi dolmuş.");
        }

        var user = resetToken.getUser();
        user.setPassword("{bcrypt}" + bCryptEncoder.encode(newPassword));
        userRepository.save(user); // Explicit save though dirty checking might work

        passwordResetTokenRepository.delete(resetToken);
        log.info("Password successfully reset for user: {}", user.getUserName());
    }

    public record AuthResponse(
            String accessToken,
            String refreshToken,
            String tokenType,
            String tenantId) {
    }

    public record TenantInfoDTO(
            Long id,
            String name,
            String slug,
            String logoUrl) {
    }
}
