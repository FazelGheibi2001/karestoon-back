package com.airbyte.charity.dto;

public class ReportBarDTO {
    private String activeUsers;
    private String users;
    private String completeProjects;

    public String getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(String activeUsers) {
        this.activeUsers = activeUsers;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getCompleteProjects() {
        return completeProjects;
    }

    public void setCompleteProjects(String completeProjects) {
        this.completeProjects = completeProjects;
    }
}
