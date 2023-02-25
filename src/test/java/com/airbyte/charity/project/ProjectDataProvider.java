package com.airbyte.charity.project;

import com.airbyte.charity.dto.FileDTO;
import com.airbyte.charity.dto.ProjectDTO;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.airbyte.charity.CommonTestData.*;

@Component
public class ProjectDataProvider {

    public ProjectDTO createEntity() {
        ProjectDTO dto = new ProjectDTO();
        dto.setTitle(DEFAULT_STRING);
        dto.setDescription(DEFAULT_STRING);
        dto.setStatus(DEFAULT_STRING);
        dto.setLikeCount(DEFAULT_BIG_DECIMAL);
        dto.setExpectedBudge(DEFAULT_BIG_DECIMAL);
        dto.setPrepareBudge(DEFAULT_BIG_DECIMAL);
        dto.setStartDate(DEFAULT_STRING);
        dto.setEndDate(DEFAULT_STRING);
        dto.setProfileId(DEFAULT_ID);
        dto.setMotivationSentence(DEFAULT_STRING);
        dto.setPriority(DEFAULT_STRING);
        dto.setFiles(List.of(prepareFile()));
        return dto;
    }

    public FileDTO prepareFile() {
        FileDTO dto = new FileDTO();
        dto.setFileId(DEFAULT_ID);
        dto.setName(DEFAULT_NAME);
        return dto;
    }

    public ProjectDTO updateEntity() {
        ProjectDTO dto = new ProjectDTO();
        dto.setTitle(UPDATED_STRING);
        dto.setDescription(UPDATED_STRING);
        dto.setStatus(UPDATED_STRING);
        dto.setLikeCount(UPDATED_BIG_DECIMAL);
        dto.setExpectedBudge(UPDATED_BIG_DECIMAL);
        dto.setPrepareBudge(UPDATED_BIG_DECIMAL);
        dto.setStartDate(UPDATED_STRING);
        dto.setEndDate(UPDATED_STRING);
        dto.setProfileId(UPDATED_ID);
        dto.setMotivationSentence(UPDATED_STRING);
        dto.setPriority(UPDATED_STRING);
        dto.setFiles(List.of(prepareFile()));
        return dto;
    }
}
