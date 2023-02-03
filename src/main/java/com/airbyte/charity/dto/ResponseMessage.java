package com.airbyte.charity.dto;

import java.io.Serializable;
import java.util.Map;

public class ResponseMessage implements Serializable {
    private Map<String, String> message;

    public ResponseMessage() {

    }

    public ResponseMessage(Map<String, String> message) {
        this.message = message;
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }
}
