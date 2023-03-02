package com.airbyte.charity.model;

import com.airbyte.charity.common.TimeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table
public class PaymentHistory implements Serializable {
    private @Id
    @Column(columnDefinition = "VARCHAR(50)", nullable = false) String id;
    private @Column(columnDefinition = "VARCHAR(255)") String projectName;
    private @Column(columnDefinition = "VARCHAR(255)") String projectId;
    private @Column(columnDefinition = "VARCHAR(255)") String amount;
    private @Column(columnDefinition = "VARCHAR(50)") String date;
    private @Column(columnDefinition = "VARCHAR(255)") String username;

    public PaymentHistory() {
        this.date = TimeConverter.convert(Date.from(Instant.now()), TimeConverter.DEFAULT_PATTERN_FORMAT);
        this.date = TimeConverter.georgianToJalaliWithHour(this.date);
    }

    public String getId() {
        return id;
    }

    @PrePersist
    public void setId() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAmount() {
        int counter = 0;
        String newAmount = "";
        char[] array = amount.toCharArray();
        for (int index = amount.length() - 1; index >= 0; index--) {
            if (counter < 3) {
                newAmount = array[index] + newAmount;
                counter++;
            } else if (counter == 3) {
                newAmount = array[index] + "," + newAmount;
                counter = 1;
            }
        }
        return newAmount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "PaymentHistory{" +
                "projectName='" + projectName + '\'' +
                ", projectId='" + projectId + '\'' +
                ", amount='" + amount + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
