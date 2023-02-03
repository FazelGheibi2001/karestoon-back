package com.airbyte.charity.model;

import com.airbyte.charity.common.FileConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Entity
@Table
public class Report implements Serializable {
    private @Id
    @Column(columnDefinition = "VARCHAR(50)", nullable = false) String id;
    private @Column(columnDefinition = "VARCHAR(255)") String title;
    private @Column(columnDefinition = "VARCHAR(255)") String description;
    private @Convert(converter = FileConverter.class)
    @Column(columnDefinition = "VARCHAR(1000)") Map<String, String> files;
    private @Column(columnDefinition = "VARCHAR(50)") String date;
    private @Column(columnDefinition = "VARCHAR(255)") String projectId;


    public String getId() {
        return id;
    }

    @PrePersist
    public void setId() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
