package com.airbyte.charity.permission;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    MANAGER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet()),
    USER(Sets.newHashSet());

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
