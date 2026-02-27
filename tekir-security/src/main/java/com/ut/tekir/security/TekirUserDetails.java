package com.ut.tekir.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Extended UserDetails that carries tenant and user ID information.
 */
@Getter
public class TekirUserDetails extends User {

    private final Long tenantId;
    private final Long userId;

    public TekirUserDetails(
            String username,
            String password,
            boolean enabled,
            Collection<? extends GrantedAuthority> authorities,
            Long tenantId,
            Long userId) {
        super(username, password, enabled, true, true, true, authorities);
        this.tenantId = tenantId;
        this.userId = userId;
    }
}
