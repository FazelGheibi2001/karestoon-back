package com.airbyte.charity.register;

import com.airbyte.charity.CharityApplication;
import com.airbyte.charity.dto.RegisterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {CharityApplication.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RegisterService service;
    @Autowired
    private RegisterDataProvider dataProvider;
    private RegisterDTO register;


    @Test
    @Transactional
    @DisplayName("REST Request to verify exist user")
    public void registerForExistUser() throws Exception {
        register = dataProvider.provideRegisterDTOForExistUser();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk())
                .andReturn();

        RegisterDTO response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), RegisterDTO.class);

        assertThat(response.getPhoneNumber()).isEqualTo(DEFAULT_USERNAME);
        assertThat(response.getStatus()).isEqualTo("exist");
    }

    @Test
    @Transactional
    @DisplayName("REST Request to verify new user")
    public void registerForNewUser() throws Exception {
        register = dataProvider.provideRegisterDTOForNewUser();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk())
                .andReturn();

        RegisterDTO response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), RegisterDTO.class);

        assertThat(response.getPhoneNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(response.getStatus()).isEqualTo("newUser");
    }

    @Test
    @Transactional
    @DisplayName("REST Request to verify when phoneNumber is Empty")
    public void registerWhenPhoneNumberIsEmpty() throws Exception {
        register = dataProvider.provideRegisterDTOWhenPhoneNumberIsEmpty();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String errorMessage = mvcResult.getResponse().getErrorMessage();

        assertThat(errorMessage).isEqualTo("phoneNumber must not be null");
    }
}
