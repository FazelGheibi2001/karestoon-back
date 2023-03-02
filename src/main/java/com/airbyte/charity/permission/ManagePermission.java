package com.airbyte.charity.permission;

public class ManagePermission {
    public static final String COMMENT_READ = "hasAuthority('comment:read')";
    public static final String COMMENT_WRITE = "hasAuthority('comment:write')";

    public static final String FILE_READ = "hasAuthority('file:read')";
    public static final String FILE_WRITE = "hasAuthority('file:write')";

    public static final String ORGANIZATION_READ = "hasAuthority('organization:read')";
    public static final String ORGANIZATION_WRITE = "hasAuthority('organization:write')";

    public static final String PAYMENT_READ = "hasAuthority('payment:read')";
    public static final String PAYMENT_WRITE = "hasAuthority('payment:write')";

    public static final String PROJECT_READ = "hasAuthority('project:read')";
    public static final String PROJECT_WRITE = "hasAuthority('project:write')";

    public static final String REPORT_READ = "hasAuthority('report:read')";
    public static final String REPORT_WRITE = "hasAuthority('report:write')";

    public static final String TICKET_READ = "hasAuthority('ticket:read')";
    public static final String TICKET_WRITE = "hasAuthority('ticket:write')";

    public static final String USER_READ = "hasAuthority('user:read')";
    public static final String USER_WRITE = "hasAuthority('user:write')";
    public static final String USER_UPDATE = "hasAuthority('user:update')";
}
