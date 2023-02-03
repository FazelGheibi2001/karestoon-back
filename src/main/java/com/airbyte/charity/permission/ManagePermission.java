package com.airbyte.charity.permission;

public class ManagePermission {
    public static final String COMMENT_READ = "hasAuthority('comment:read')";
    public static final String COMMENT_WRITE = "hasAuthority('comment:write')";
    public static final String ORGANIZATION_READ = "hasAuthority('organization:read')";
    public static final String ORGANIZATION_WRITE = "hasAuthority('organization:write')";
}
