package com.airbyte.charity.ticket;

import com.airbyte.charity.CharityApplication;
import com.airbyte.charity.dto.TicketDTO;
import com.airbyte.charity.model.Ticket;
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
public class TicketControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TicketService service;
    @Autowired
    private TicketDataProvider dataProvider;
    private TicketDTO ticket;

    @BeforeEach
    public void initTest() {
        ticket = dataProvider.createEntity();
    }

    @Test
    @Transactional
    @DisplayName("REST Request to save ticket")
    public void saveTicket() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();

        mockMvc.perform(post("/api/v1/ticket")
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isCreated());

        Ticket entity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(entity.getId()).isNotBlank();
        assertThat(entity.getDate()).isNotNull();
        assertThat(entity.getStatus()).isEqualTo(DEFAULT_STRING);
        assertThat(entity.getUserId()).isEqualTo(DEFAULT_ID);
        assertThat(entity.getTitle()).isEqualTo(DEFAULT_STRING);
        entity.getChatList()
                .forEach(chat -> {
                    assertThat(chat.getDate()).isNotNull();
                    assertThat(chat.getSender()).isEqualTo(DEFAULT_NAME);
                    assertThat(chat.getMessage()).isEqualTo(DEFAULT_STRING);
                });
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get tickets")
    public void getTickets() throws Exception {
        Ticket entity = service.save(ticket);

        mockMvc.perform(get("/api/v1/ticket")
                        .header("Authorization", token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(entity.getId()))
                .andExpect(jsonPath("$.[0].date").isNotEmpty())
                .andExpect(jsonPath("$.[0].status").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].userId").value(DEFAULT_ID))
                .andExpect(jsonPath("$.[0].title").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.[0].chatList.[0].date").isNotEmpty())
                .andExpect(jsonPath("$.[0].chatList.[0].sender").value(DEFAULT_NAME))
                .andExpect(jsonPath("$.[0].chatList.[0].message").value(DEFAULT_STRING));
    }

    @Test
    @Transactional
    @DisplayName("REST Request to get ticket By Id")
    public void getTicketById() throws Exception {
        Ticket entity = service.save(ticket);

        mockMvc.perform(get("/api/v1/ticket/{id}", entity.getId())
                        .header("Authorization", token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.status").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.userId").value(DEFAULT_ID))
                .andExpect(jsonPath("$.title").value(DEFAULT_STRING))
                .andExpect(jsonPath("$.chatList.[0].date").isNotEmpty())
                .andExpect(jsonPath("$.chatList.[0].sender").value(DEFAULT_NAME))
                .andExpect(jsonPath("$.chatList.[0].message").value(DEFAULT_STRING));
    }

    @Test
    @Transactional
    @DisplayName("REST Request to update ticket")
    public void updateTicket() throws Exception {
        int databaseSizeBeforeSave = service.getAll().size();
        Ticket entity = service.save(ticket);

        ticket = dataProvider.updateEntity();

        mockMvc.perform(put("/api/v1/ticket/{id}", entity.getId())
                        .header("Authorization", token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isAccepted());

        Ticket newEntity = service.getAll().get(databaseSizeBeforeSave);

        assertThat(service.getAll()).hasSize(databaseSizeBeforeSave + 1);
        assertThat(newEntity.getId()).isNotBlank();
        assertThat(newEntity.getDate()).isNotNull();
        assertThat(newEntity.getStatus()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getUserId()).isEqualTo(DEFAULT_ID);
        assertThat(newEntity.getTitle()).isEqualTo(UPDATED_STRING);
        assertThat(newEntity.getChatList().size()).isEqualTo(2);
        newEntity.getChatList()
                .forEach(chat -> {
                    assertThat(chat.getDate()).isNotNull();
                    assertThat(chat.getSender()).isNotNull();
                    assertThat(chat.getMessage()).isNotNull();
                });
    }

    @Test
    @Transactional
    @DisplayName("REST Request to delete ticket")
    public void deleteTicket() throws Exception {
        Ticket entity = service.save(ticket);
        int databaseSizeAfterSave = service.getAll().size();

        mockMvc.perform(delete("/api/v1/ticket/{id}", entity.getId())
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
