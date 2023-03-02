package com.airbyte.charity.permission;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.airbyte.charity.permission.Permission.*;

public enum Role {
    MANAGER(Sets.newHashSet(COMMENT_READ, COMMENT_WRITE, FILE_READ, FILE_WRITE, ORGANIZATION_READ, ORGANIZATION_WRITE, PAYMENT_READ, PAYMENT_WRITE, PROJECT_READ, PROJECT_WRITE, REPORT_READ, REPORT_WRITE, TICKET_READ, TICKET_WRITE, USER_READ, USER_WRITE, USER_UPDATE)),
    ADMIN(Sets.newHashSet(COMMENT_READ, COMMENT_WRITE, FILE_READ, FILE_WRITE, ORGANIZATION_READ, ORGANIZATION_WRITE, PAYMENT_READ, PAYMENT_WRITE, PROJECT_READ, PROJECT_WRITE, REPORT_READ, REPORT_WRITE, TICKET_READ, TICKET_WRITE, USER_READ, USER_UPDATE)),
    USER(Sets.newHashSet(COMMENT_READ, COMMENT_WRITE, FILE_READ, ORGANIZATION_READ, PAYMENT_READ, PAYMENT_WRITE, PROJECT_READ, REPORT_READ, TICKET_READ, TICKET_WRITE, USER_UPDATE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> grantedAuthorities = this.getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        grantedAuthorities.add(new SimpleGrantedAuthority("Role: " + this.name()));
        return grantedAuthorities;
    }
}
