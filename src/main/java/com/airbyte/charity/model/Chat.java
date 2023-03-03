package com.airbyte.charity.model;

import com.airbyte.charity.common.TimeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table
public class Chat implements Serializable {
    private @Id
    @Column(columnDefinition = "VARCHAR(50)", nullable = false) String id;
    private @Column(columnDefinition = "VARCHAR(50)") String message;
    private @Column(columnDefinition = "VARCHAR(50)") String sender;
    private @Column(columnDefinition = "VARCHAR(50)") String date;
    private @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id") Ticket ticket;

    public Chat() {
        this.date = TimeConverter.convert(Date.from(Instant.now()), TimeConverter.UPDATED_PATTERN_FORMAT);
        this.date = TimeConverter.georgianToJalali(this.date);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    @PrePersist
    public void setId() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
