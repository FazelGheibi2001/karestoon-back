package com.airbyte.charity.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class FileDTO implements Serializable {
    private @NotBlank String name;
    private @NotBlank String fileId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
