package com.airbyte.charity.permission;

public enum Permission {
    COMMENT_READ("comment:read"),
    COMMENT_WRITE("comment:write"),

    FILE_READ("file:read"),
    FILE_WRITE("file:write"),

    ORGANIZATION_READ("organization:read"),
    ORGANIZATION_WRITE("organization:write"),

    PAYMENT_READ("payment:read"),
    PAYMENT_WRITE("payment:write"),

    PROJECT_READ("project:read"),
    PROJECT_WRITE("project:write"),

    REPORT_READ("report:read"),
    REPORT_WRITE("report:write"),

    TICKET_READ("ticket:read"),
    TICKET_WRITE("ticket:write"),

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_UPDATE("user:update");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
