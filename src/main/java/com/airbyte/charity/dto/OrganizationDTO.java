package com.airbyte.charity.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class OrganizationDTO implements Serializable {
    private String name;
    private String address;
    private String description;
    private @NotNull String projectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
