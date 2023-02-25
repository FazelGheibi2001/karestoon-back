package com.airbyte.charity.project;

import com.airbyte.charity.CharityApplication;
import com.airbyte.charity.dto.ProjectDTO;
import com.airbyte.charity.model.Project;
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
public class ProjectControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProjectService service;
    @Autowired
    private ProjectDataProvider dataProvider;
    private ProjectDTO project;

    @BeforeEach
    public void initTest() {
        project = dataProvider.createEntity();
    }

    @Test
    @Transactional
    @DisplayName("REST Request to save project")
    public void saveProject() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();

        mockMvc.perform(post("/api/v1/project")
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isCreated());

        Project entity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(entity.getId()).isNotBlank();
        assertThat(entity.getTitle()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getDescription()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getStatus()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getLikeCount()).isEqualTo(DEFAULT_BIG_DECIMAL);
        assertThat(entity.getExpectedBudge()).isEqualTo(DEFAULT_BIG_DECIMAL);
        assertThat(entity.getPrepareBudge()).isEqualTo(DEFAULT_BIG_DECIMAL);
        assertThat(entity.getStartDate()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getEndDate()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getProfileId()).isNotNull();
        assertThat(entity.getMotivationSentence()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getPriority()).isEqualTo(DEFAULT_STRING);
        entity.getFiles()
                .forEach((id, name) -> {
                    assertThat(id).isNotNull();
                    assertThat(name).isNotNull();
                });
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get projects")
    public void getProjects() throws Exception {
        Project entity = service.save(project);

        mockMvc.perform(get("/api/v1/project"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(entity.getId()))
                .andExpect(jsonPath("$.[0].title").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].status").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].likeCount").value(DEFAULT_BIG_DECIMAL))
                .andExpect(jsonPath("$.[0].expectedBudge").value(DEFAULT_BIG_DECIMAL))
                .andExpect(jsonPath("$.[0].prepareBudge").value(DEFAULT_BIG_DECIMAL))
                .andExpect(jsonPath("$.[0].startDate").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].endDate").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].profileId").isNotEmpty())
                .andExpect(jsonPath("$.[0].motivationSentence").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].priority").value(DEFAULT_STRING));
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get project")
    public void getProject() throws Exception {
        Project entity = service.save(project);

        mockMvc.perform(get("/api/v1/project/{id}", entity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.title").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.description").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.status").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.likeCount").value(DEFAULT_BIG_DECIMAL))
                .andExpect(jsonPath("$.expectedBudge").value(DEFAULT_BIG_DECIMAL))
                .andExpect(jsonPath("$.prepareBudge").value(DEFAULT_BIG_DECIMAL))
                .andExpect(jsonPath("$.startDate").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.endDate").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.profileId").isNotEmpty())
                .andExpect(jsonPath("$.motivationSentence").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.priority").value(DEFAULT_STRING));
    }


    @Test
    @Transactional
    @DisplayName("REST Request to update comment")
    public void updateProject() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();
        Project entity = service.save(project);

        project = dataProvider.updateEntity();

        mockMvc.perform(put("/api/v1/project/{id}", entity.getId())
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isAccepted());

        Project newEntity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(newEntity.getId()).isNotBlank();
        assertThat(newEntity.getTitle()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getDescription()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getStatus()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getLikeCount()).isEqualTo(UPDATED_BIG_DECIMAL);
        assertThat(newEntity.getExpectedBudge()).isEqualTo(UPDATED_BIG_DECIMAL);
        assertThat(newEntity.getPrepareBudge()).isEqualTo(UPDATED_BIG_DECIMAL);
        assertThat(newEntity.getStartDate()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getEndDate()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getProfileId()).isNotNull();
        assertThat(newEntity.getMotivationSentence()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getPriority()).isEqualTo(UPDATED_STRING);
        entity.getFiles()
                .forEach((id, name) -> {
                    assertThat(id).isNotNull();
                    assertThat(name).isNotNull();
                });
    }

    @Test
    @Transactional
    @DisplayName("REST Request to delete project")
    public void deleteProject() throws Exception {
        Project entity = service.save(project);
        int databaseSizeAfterSave = service.getAll().size();

        mockMvc.perform(delete("/api/v1/project/{id}", entity.getId())
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
