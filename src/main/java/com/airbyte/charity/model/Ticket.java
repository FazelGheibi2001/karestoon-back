package com.airbyte.charity.model;

import com.airbyte.charity.common.TimeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table
public class Ticket implements Serializable {
    private @Id
    @Column(columnDefinition = "VARCHAR(50)", nullable = false) String id;
    private @Column(columnDefinition = "VARCHAR(255)") String sender;
    private @Column(columnDefinition = "VARCHAR(255)") String title;
    private @Column(columnDefinition = "VARCHAR(4000)") String request;
    private @Column(columnDefinition = "VARCHAR(50)") String senderProfile;
    private @Column(columnDefinition = "VARCHAR(4000)") String response;
    private @Column(columnDefinition = "VARCHAR(50)") String date;
    private @Column(columnDefinition = "VARCHAR(50)") String userId;

    public Ticket() {
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getSenderProfile() {
        return senderProfile;
    }

    public void setSenderProfile(String senderProfile) {
        this.senderProfile = senderProfile;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "sender='" + sender + '\'' +
                ", request='" + request + '\'' +
                ", senderProfile='" + senderProfile + '\'' +
                ", response='" + response + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
