package com.ut.tekir.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${tekir.security.jwt.secret}")
    private String secretKey;

    @Value("${tekir.security.jwt.expiration:86400000}")
    private long jwtExpiration; // default 24 hours

    @Value("${tekir.security.jwt.refresh-expiration:604800000}")
    private long refreshExpiration; // default 7 days

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractTenantId(String token) {
        String tenantIdStr = extractClaim(token, claims -> claims.get("tenantId", String.class));
        return tenantIdStr != null ? Long.parseLong(tenantIdStr) : null;
    }

    public Long extractUserId(String token) {
        String userIdStr = extractClaim(token, claims -> claims.get("userId", String.class));
        return userIdStr != null ? Long.parseLong(userIdStr) : null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Add tenant-specific claims if available
        if (userDetails instanceof TekirUserDetails tekirUser) {
            extraClaims.putIfAbsent("tenantId", String.valueOf(tekirUser.getTenantId()));
            extraClaims.putIfAbsent("userId", String.valueOf(tekirUser.getUserId()));

            List<String> roles = tekirUser.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(a -> a.startsWith("ROLE_"))
                    .map(a -> a.substring(5))
                    .collect(Collectors.toList());
            extraClaims.putIfAbsent("roles", roles);

            List<String> permissions = tekirUser.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(a -> !a.startsWith("ROLE_"))
                    .collect(Collectors.toList());
            if (!permissions.isEmpty()) {
                extraClaims.putIfAbsent("permissions", permissions);
            }
        }
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof TekirUserDetails tekirUser) {
            claims.put("tenantId", String.valueOf(tekirUser.getTenantId()));
            claims.put("userId", String.valueOf(tekirUser.getUserId()));
        }
        return buildToken(claims, userDetails, refreshExpiration);
    }

    /**
     * Generate a short-lived impersonation token for super admin support access.
     */
    public String generateImpersonationToken(UserDetails userDetails, Long targetTenantId, String impersonatedBy) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tenantId", String.valueOf(targetTenantId));
        claims.put("impersonatedBy", impersonatedBy);
        if (userDetails instanceof TekirUserDetails tekirUser) {
            claims.put("userId", String.valueOf(tekirUser.getUserId()));
        }
        long oneHour = 3600000L;
        return buildToken(claims, userDetails, oneHour);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey())
            .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

