package com.airbyte.charity.user;

import com.airbyte.charity.CharityApplication;
import com.airbyte.charity.dto.UserDTO;
import com.airbyte.charity.model.UserInformation;
import com.airbyte.charity.permission.Role;
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
public class UserInformationControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserInformationService service;
    @Autowired
    private UserInformationDataProvider dataProvider;
    private UserDTO user;

    @BeforeEach
    public void initTest() {
        user = dataProvider.createEntity();
    }

    @Test
    @Transactional
    @DisplayName("REST Request to save user")
    public void saveUser() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();

        mockMvc.perform(post("/api/v1/user")
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());

        UserInformation entity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(entity.getId()).isNotBlank();
        assertThat(entity.getUsername()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getPassword()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getRole()).isEqualTo(Role.ADMIN);
        assertThat(entity.getFirstName()).isEqualTo(DEFAULT_NAME);
        assertThat(entity.getLastName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get users")
    public void getUsers() throws Exception {
        UserInformation entity = service.save(user);

        mockMvc.perform(get("/api/v1/user")
                        .header("Authorization", token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[2].id").value(entity.getId()))
                .andExpect(jsonPath("$.[2].username").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[2].password").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[2].role").value(Role.ADMIN.name()))
                .andExpect(jsonPath("$.[2].firstName").value(DEFAULT_NAME))
                .andExpect(jsonPath("$.[2].lastName").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get comment By Id")
    public void getUserById() throws Exception {
        UserInformation entity = service.save(user);

        mockMvc.perform(get("/api/v1/user/{id}", entity.getId())
                        .header("Authorization", token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.username").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.password").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.role").value(Role.ADMIN.name()))
                .andExpect(jsonPath("$.firstName").value(DEFAULT_NAME))
                .andExpect(jsonPath("$.lastName").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    @DisplayName("REST Request to update user")
    public void updateUser() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();
        UserInformation entity = service.save(user);

        user = dataProvider.updateEntity();

        mockMvc.perform(put("/api/v1/user/{id}", entity.getId())
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isAccepted());

        UserInformation newEntity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(newEntity.getId()).isNotBlank();
        assertThat(newEntity.getLastName()).isEqualTo(UPDATED_NAME);
        assertThat(newEntity.getFirstName()).isEqualTo(UPDATED_NAME);
        assertThat(newEntity.getUsername()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getPassword()).isEqualTo(UPDATED_STRING);
        assertThat(entity.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    @Transactional
    @DisplayName("REST Request to delete user")
    public void deleteComment() throws Exception {
        UserInformation entity = service.save(user);
        int databaseSizeAfterSave = service.getAll().size();

        mockMvc.perform(delete("/api/v1/user/{id}", entity.getId())
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
