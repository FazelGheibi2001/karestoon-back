package com.airbyte.charity.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProjectDTO {
    private String title;
    private String description;
    private String status;
    private BigDecimal likeCount;
    private BigDecimal expectedBudge;
    private BigDecimal prepareBudge;
    private String startDate;
    private String endDate;
    private String profileId;
    private String motivationSentence;
    private String priority;
    private List<FileDTO> files;

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

    public List<FileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileDTO> files) {
        this.files = files;
    }
}
