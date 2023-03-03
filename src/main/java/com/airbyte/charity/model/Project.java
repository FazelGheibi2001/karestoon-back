package com.airbyte.charity.model;

import com.airbyte.charity.common.FileConverter;
import com.airbyte.charity.dto.CommentDTO;
import com.airbyte.charity.dto.FileDTO;
import com.airbyte.charity.dto.ReportDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table
public class Project implements Serializable {
    private @Id
    @Column(columnDefinition = "VARCHAR(50)", nullable = false) String id;
    private @Column(columnDefinition = "VARCHAR(255)") String title;
    private @Column(columnDefinition = "VARCHAR(255)") String description;
    private @Column(columnDefinition = "VARCHAR(255)") String status;
    private @Column(columnDefinition = "NUMERIC(20, 0)") BigDecimal likeCount;
    private @Column(columnDefinition = "NUMERIC(30, 4)") BigDecimal expectedBudge;
    private @Column(columnDefinition = "NUMERIC(30, 4)") BigDecimal prepareBudge;
    private @Column(columnDefinition = "VARCHAR(255)") String startDate;
    private @Column(columnDefinition = "VARCHAR(255)") String endDate;
    private @Column(columnDefinition = "VARCHAR(50)") String profileId;
    private @Column(columnDefinition = "VARCHAR(255)") String motivationSentence;
    private @Column(columnDefinition = "VARCHAR(255)") String priority;
    private @Convert(converter = FileConverter.class)
    @Column(columnDefinition = "VARCHAR(1000)") Map<String, String> files;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(BigDecimal likeCount) {
        this.likeCount = likeCount;
    }

    public BigDecimal getExpectedBudge() {
        return expectedBudge;
    }

    public void setExpectedBudge(BigDecimal expectedBudge) {
        this.expectedBudge = expectedBudge;
    }

    public BigDecimal getPrepareBudge() {
        return prepareBudge;
    }

    public void setPrepareBudge(BigDecimal prepareBudge) {
        this.prepareBudge = prepareBudge;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getMotivationSentence() {
        return motivationSentence;
    }

    public void setMotivationSentence(String motivationSentence) {
        this.motivationSentence = motivationSentence;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    public Integer getProgress() {
        if (prepareBudge != null && expectedBudge != null) {
            return (int) ((prepareBudge.doubleValue() / expectedBudge.doubleValue()) * 100);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Project{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", like=" + likeCount +
                ", expectedBudge=" + expectedBudge +
                ", prepareBudge=" + prepareBudge +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", profileId='" + profileId + '\'' +
                ", motivationSentence='" + motivationSentence + '\'' +
                ", priority='" + priority + '\'' +
                ", files=" + files +
                '}';
    }
}
