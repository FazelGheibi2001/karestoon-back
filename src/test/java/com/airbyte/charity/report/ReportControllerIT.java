package com.airbyte.charity.report;

import com.airbyte.charity.CharityApplication;
import com.airbyte.charity.dto.ReportDTO;
import com.airbyte.charity.model.Report;
import com.airbyte.charity.user.UserInformationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static com.airbyte.charity.CommonTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {CharityApplication.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReportService service;
    @Autowired
    private ReportDataProvider dataProvider;
    private ReportDTO report;

    @BeforeEach
    public void initTest() {
        report = dataProvider.createEntity();
    }

    @Test
    @Transactional
    @DisplayName("REST Request to save report")
    public void saveReport() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();

        mockMvc.perform(post("/api/v1/report")
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(report)))
                .andExpect(status().isCreated());

        Report entity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(entity.getId()).isNotBlank();
        assertThat(entity.getDate()).isNotNull();
        assertThat(entity.getDescription()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getTitle()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getProjectId()).isEqualTo(DEFAULT_STRING);
        entity.getFiles()
                .forEach((name, id) -> {
                    assertThat(id).isEqualTo(DEFAULT_ID);
                    assertThat(name).isEqualTo(DEFAULT_NAME);
                });
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get reports")
    public void getReports() throws Exception {
        Report entity = service.save(report);

        mockMvc.perform(get("/api/v1/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(entity.getId()))
                .andExpect(jsonPath("$.[0].date").isNotEmpty())
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].title").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].projectId").value(DEFAULT_STRING));
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get report By Id")
    public void getReportById() throws Exception {
        Report entity = service.save(report);

        mockMvc.perform(get("/api/v1/report/{id}", entity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.description").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.title").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.projectId").value(DEFAULT_STRING));
    }

    @Test
    @Transactional
    @DisplayName("REST Request to update report")
    public void updateReport() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();
        Report entity = service.save(report);

        report = dataProvider.updateEntity();

        mockMvc.perform(put("/api/v1/report/{id}", entity.getId())
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(report)))
                .andExpect(status().isAccepted());

        Report newEntity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(newEntity.getId()).isNotBlank();
        assertThat(newEntity.getDate()).isNotNull();
        assertThat(newEntity.getDescription()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getTitle()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getProjectId()).isEqualTo(DEFAULT_STRING);
        newEntity.getFiles()
                .forEach((name, id) -> {
                    assertThat(id).isEqualTo(DEFAULT_ID);
                    assertThat(name).isEqualTo(DEFAULT_NAME);
                });
    }

    @Test
    @Transactional
    @DisplayName("REST Request to delete report")
    public void deleteReport() throws Exception {
        Report entity = service.save(report);
        int databaseSizeAfterSave = service.getAll().size();

        mockMvc.perform(delete("/api/v1/report/{id}", entity.getId())
                        .header("Authorization", token()))
                .andExpect(status().isNoContent());

        assertThat(service.getAll()).hasSize(databaseSizeAfterSave - 1);
    }

    private String token() {
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginDTO())))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mvcResult.getResponse().getHeader("Authorization");
    }
}
