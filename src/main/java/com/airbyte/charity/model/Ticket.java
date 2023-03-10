package com.airbyte.charity.model;

import com.airbyte.charity.common.TimeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class Ticket implements Serializable {
    private @Id
    @Column(columnDefinition = "VARCHAR(50)", nullable = false) String id;
    private @Column(columnDefinition = "VARCHAR(255)") String title;
    private @Column(columnDefinition = "VARCHAR(255)") String status;
    private @Column(columnDefinition = "VARCHAR(50)") String userId;
    private @Column(columnDefinition = "VARCHAR(50)") String date;
    private @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Chat> chatList = new ArrayList<>();

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

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                ", chatList=" + chatList +
                '}';
    }
}
