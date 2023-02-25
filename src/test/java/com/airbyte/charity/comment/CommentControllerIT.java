package com.airbyte.charity.comment;

import com.airbyte.charity.CharityApplication;
import com.airbyte.charity.dto.CommentDTO;
import com.airbyte.charity.model.Comment;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {CharityApplication.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentService service;
    @Autowired
    private CommentDataProvider dataProvider;
    private CommentDTO comment;

    @BeforeEach
    public void initTest() {
        comment = dataProvider.createEntity();
    }

    @Test
    @Transactional
    @DisplayName("REST Request to save comment")
    public void saveComment() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();

        mockMvc.perform(post("/api/v1/comment")
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isCreated());

        Comment entity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(entity.getId()).isNotBlank();
        assertThat(entity.getDate()).isNotNull();
        assertThat(entity.getLikeCount().longValue()).isEqualTo(DEFAULT_LONG);
        assertThat(entity.getDisLikeCount().longValue()).isEqualTo(DEFAULT_LONG);
        assertThat(entity.getSenderName()).isEqualTo(DEFAULT_STRING);
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get comments")
    public void getComments() throws Exception {
        Comment entity = service.save(comment);

        mockMvc.perform(get("/api/v1/comment")
                        .header("Authorization", token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(entity.getId()))
                .andExpect(jsonPath("$.[0].senderName").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].text").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].projectId").value(DEFAULT_STRING));
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get comment By Id")
    public void getCommentById() throws Exception {
        Comment entity = service.save(comment);

        mockMvc.perform(get("/api/v1/comment/{id}", entity.getId())
                        .header("Authorization", token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.senderName").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.text").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.projectId").value(DEFAULT_STRING));
    }

    @Test
    @Transactional
    @DisplayName("REST Request to update comment")
    public void updateComment() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();
        Comment entity = service.save(comment);

        comment = dataProvider.updateEntity();

        mockMvc.perform(put("/api/v1/comment/{id}", entity.getId())
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isAccepted());

        Comment newEntity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(newEntity.getId()).isNotBlank();
        assertThat(newEntity.getDate()).isNotNull();
        assertThat(newEntity.getLikeCount().longValue()).isEqualTo(UPDATED_LONG);
        assertThat(newEntity.getDisLikeCount().longValue()).isEqualTo(UPDATED_LONG);
        assertThat(newEntity.getSenderName()).isEqualTo(UPDATED_STRING);
    }

    @Test
    @Transactional
    @DisplayName("REST Request to delete comment")
    public void deleteComment() throws Exception {
        Comment entity = service.save(comment);
        int databaseSizeAfterSave = service.getAll().size();

        mockMvc.perform(delete("/api/v1/comment/{id}", entity.getId())
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
