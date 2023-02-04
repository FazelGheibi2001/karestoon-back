package com.airbyte.charity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

public class CommentDTO {
    private String senderName;
    private String text;
    private String date;
    private Long likeCount;
    private Long disLikeCount;
    private String projectId;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getDisLikeCount() {
        return disLikeCount;
    }

    public void setDisLikeCount(Long disLikeCount) {
        this.disLikeCount = disLikeCount;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
