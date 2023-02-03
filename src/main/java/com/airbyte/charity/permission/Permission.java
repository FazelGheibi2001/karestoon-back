package com.airbyte.charity.permission;

public enum Permission {
    ORGANIZATION_READ("organization:read"),
    ORGANIZATION_WRITE("organization:write"),
    COMMENT_READ("comment:read"),
    COMMENT_WRITE("comment:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
