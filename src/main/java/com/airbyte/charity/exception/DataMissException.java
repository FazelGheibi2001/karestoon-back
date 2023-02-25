package com.airbyte.charity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DataMissException extends ResponseStatusException {

    public DataMissException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }
}
