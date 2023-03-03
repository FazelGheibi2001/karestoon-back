package com.airbyte.charity.dto;

public class ReportBarDTO {
    private Long activeUsers;
    private Long users;
    private Long completeProjects;

    public Long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public Long getUsers() {
        return users;
    }

    public void setUsers(Long users) {
        this.users = users;
    }

    public Long getCompleteProjects() {
        return completeProjects;
    }

    public void setCompleteProjects(Long completeProjects) {
        this.completeProjects = completeProjects;
    }
}
