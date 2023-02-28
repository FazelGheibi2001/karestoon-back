package com.airbyte.charity.payment;

import com.airbyte.charity.CharityApplication;
import com.airbyte.charity.dto.PaymentHistoryDTO;
import com.airbyte.charity.model.PaymentHistory;
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
public class PaymentControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PaymentHistoryService service;
    @Autowired
    private PaymentDataProvider dataProvider;
    private PaymentHistoryDTO payment;

    @BeforeEach
    public void initTest() {
        payment = dataProvider.createEntity();
    }

    @Test
    @Transactional
    @DisplayName("REST Request to save payment")
    public void saveComment() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();

        mockMvc.perform(post("/api/v1/payment")
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isCreated());

        PaymentHistory entity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(entity.getId()).isNotBlank();
        assertThat(entity.getDate()).isNotNull();
        assertThat(entity.getAmount()).isNotNull();
        assertThat(entity.getProjectId()).isNotNull();
        assertThat(entity.getProjectName()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getUsername()).isEqualTo(DEFAULT_USERNAME);
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get payments")
    public void getPayments() throws Exception {
        PaymentHistory entity = service.save(payment);

        mockMvc.perform(get("/api/v1/payment")
                        .header("Authorization", token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(entity.getId()))
                .andExpect(jsonPath("$.[0].date").isNotEmpty())
                .andExpect(jsonPath("$.[0].amount").isNotEmpty())
                .andExpect(jsonPath("$.[0].projectId").isNotEmpty())
                .andExpect(jsonPath("$.[0].projectName").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].username").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get payment By Id")
    public void getPaymentById() throws Exception {
        PaymentHistory entity = service.save(payment);

        mockMvc.perform(get("/api/v1/payment/{id}", entity.getId())
                        .header("Authorization", token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.amount").isNotEmpty())
                .andExpect(jsonPath("$.projectId").isNotEmpty())
                .andExpect(jsonPath("$.projectName").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.username").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("REST Request to update payment")
    public void updatePayment() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();
        PaymentHistory entity = service.save(payment);

        payment = dataProvider.updateEntity();

        mockMvc.perform(put("/api/v1/payment/{id}", entity.getId())
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isAccepted());

        PaymentHistory newEntity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(newEntity.getId()).isNotBlank();
        assertThat(newEntity.getDate()).isNotNull();
        assertThat(newEntity.getAmount()).isNotNull();
        assertThat(newEntity.getProjectId()).isNotNull();
        assertThat(newEntity.getProjectName()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getUsername()).isEqualTo(DEFAULT_USERNAME);
    }

    @Test
    @Transactional
    @DisplayName("REST Request to delete payment")
    public void deleteComment() throws Exception {
        PaymentHistory entity = service.save(payment);
        int databaseSizeAfterSave = service.getAll().size();

        mockMvc.perform(delete("/api/v1/payment/{id}", entity.getId())
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
