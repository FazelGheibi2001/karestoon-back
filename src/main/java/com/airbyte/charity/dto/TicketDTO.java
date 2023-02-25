package com.airbyte.charity.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class TicketDTO {
    private String date;
    private String userId;
    private String title;
    private List<ChatDTO> chatList;
    private String status;

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

    public List<ChatDTO> getChatList() {
        return chatList;
    }

    public void setChatList(List<ChatDTO> chatList) {
        this.chatList = chatList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
