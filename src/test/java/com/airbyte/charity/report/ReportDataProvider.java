package com.airbyte.charity.report;

import com.airbyte.charity.dto.FileDTO;
import com.airbyte.charity.dto.ReportDTO;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.airbyte.charity.CommonTestData.*;

@Component
public class ReportDataProvider {

    public ReportDTO createEntity() {
        ReportDTO dto = new ReportDTO();
        dto.setTitle(DEFAULT_STRING);
        dto.setDescription(DEFAULT_STRING);
        dto.setProfileId(DEFAULT_ID);
        dto.setDate(DEFAULT_DATE);
        dto.setProjectId(DEFAULT_STRING);
        return dto;
    }

    public ReportDTO updateEntity() {
        ReportDTO dto = new ReportDTO();
        dto.setTitle(UPDATED_STRING);
        dto.setDescription(UPDATED_STRING);
        dto.setProfileId(UPDATED_ID);
        dto.setDate(UPDATED_DATE);
        dto.setProjectId(UPDATED_STRING);
        return dto;
    }
}
