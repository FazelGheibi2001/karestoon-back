package com.airbyte.charity.model;

import com.airbyte.charity.common.TimeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

@Entity
@Table
public class Comment implements Serializable, Comparator<Comment> {
    private @Id
    @Column(columnDefinition = "VARCHAR(50)", nullable = false) String id;
    private @Column(columnDefinition = "VARCHAR(255)") String senderName;
    private @Column(columnDefinition = "VARCHAR(4000)") String text;
    private @Column(columnDefinition = "VARCHAR(255)") String date;
    private @Column(columnDefinition = "NUMERIC(10, 0)") BigDecimal likeCount;
    private @Column(columnDefinition = "NUMERIC(10, 0)") BigDecimal disLikeCount;
    private Double performance;
    private @Column(columnDefinition = "VARCHAR(50)") String projectId;

    public Comment() {
        this.date = TimeConverter.convert(Date.from(Instant.now()), TimeConverter.UPDATED_PATTERN_FORMAT);
        this.date = TimeConverter.georgianToJalali(this.date);
    }


    public String getId() {
        return id;
    }

    @PrePersist
    public void setId() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(BigDecimal likeCount) {
        this.likeCount = likeCount;
    }

    public BigDecimal getDisLikeCount() {
        return disLikeCount;
    }

    public void setDisLikeCount(BigDecimal disLikeCount) {
        this.disLikeCount = disLikeCount;
    }

    public Double getPerformance() {
        return performance;
    }

    public void setPerformance(Double performance) {
        this.performance = performance;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "senderName='" + senderName + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", like=" + likeCount +
                ", disLike=" + disLikeCount +
                '}';
    }

    @Override
    public int compare(Comment o1, Comment o2) {
        return (int) (o1.getPerformance() - o2.getPerformance());
    }
}
