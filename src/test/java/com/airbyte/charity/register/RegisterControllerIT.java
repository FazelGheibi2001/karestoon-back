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
import static com.airbyte.charity.register.OTPDatabase.VALID_PHONE_NUMBER;
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

    @Test
    @Transactional
    @DisplayName("REST Request to check OTP when password is gone")
    public void checkOTPWhenPhoneNumberIsGone() throws Exception {
        register = dataProvider.provideRegisterDTOCheckOTP();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/verify/checkOTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String errorMessage = mvcResult.getResponse().getErrorMessage();

//        assertThat(errorMessage).isEqualTo("The password has been gone. try again.");
    }

    @Test
    @Transactional
    @DisplayName("REST Request to check OTP SuccessFull")
    public void checkOTPSuccessFull() throws Exception {
        register = dataProvider.provideRegisterDTOForNewUser();

        mockMvc.perform(post("/api/v1/register/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        register = dataProvider.provideRegisterDTOCheckOTP();

        mockMvc.perform(post("/api/v1/register/verify/checkOTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DisplayName("REST Request to check OTP is Invalid")
    public void checkOTPInvalid() throws Exception {
        register = dataProvider.provideRegisterDTOForNewUser();

        mockMvc.perform(post("/api/v1/register/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        register = dataProvider.provideRegisterDTOCheckOTPInvalid();

        mockMvc.perform(post("/api/v1/register/verify/checkOTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isInternalServerError());
    }


    @Test
    @Transactional
    @DisplayName("REST Request to create User when Data miss")
    public void createUserWhenDataMiss() throws Exception {
        register = dataProvider.createUser();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String errorMessage = mvcResult.getResponse().getErrorMessage();

//        assertThat(errorMessage).isEqualTo("Can not do anything");
    }

    @Test
    @Transactional
    @DisplayName("REST Request to create user")
    public void createUserSuccessFully() throws Exception {
        register = dataProvider.provideRegisterDTOForNewUser();

        mockMvc.perform(post("/api/v1/register/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        register = dataProvider.provideRegisterDTOCheckOTP();

        mockMvc.perform(post("/api/v1/register/verify/checkOTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        register = dataProvider.createUser();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isCreated())
                .andReturn();

        RegisterDTO response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), RegisterDTO.class);

        assertThat(response.getStatus()).isEqualTo("create");

    }


    @Test
    @Transactional
    @DisplayName("REST Request to create user")
    public void createUserUnSuccessFully() throws Exception {
        register = dataProvider.provideRegisterDTOForNewUser();

        mockMvc.perform(post("/api/v1/register/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        register = dataProvider.provideRegisterDTOCheckOTP();

        mockMvc.perform(post("/api/v1/register/verify/checkOTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        register = dataProvider.createUser();
        VALID_PHONE_NUMBER.put(DEFAULT_MOBILE_NUMBER, false);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        RegisterDTO response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), RegisterDTO.class);

        assertThat(response.getStatus()).isEqualTo("userCanNotCreated");

    }

    @Test
    @Transactional
    @DisplayName("REST Request to forgot password")
    public void forgotPasswordWhenNewUser() throws Exception {
        register = dataProvider.forgotPasswordNewUser();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/forgotPassword")
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
    @DisplayName("REST Request to forgot password")
    public void forgotPasswordWithExistUser() throws Exception {
        register = dataProvider.forgotPasswordExistUser();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/forgotPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk())
                .andReturn();

        RegisterDTO response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), RegisterDTO.class);

        assertThat(response.getPhoneNumber()).isEqualTo(DEFAULT_USERNAME);
        assertThat(response.getStatus()).isEqualTo("OTPSent");
    }

    @Test
    @Transactional
    @DisplayName("REST Request to forgot password when password is Gone")
    public void forgotPasswordCheckOTPWhenPasswordIsGone() throws Exception {
        register = dataProvider.forgotPasswordExistUser();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/forgotPassword/checkOTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String message = mvcResult.getResponse().getErrorMessage();

//        assertThat(message).isEqualTo("The password has been gone. try again.");
    }


    @Test
    @Transactional
    @DisplayName("REST Request to forgot password Accepted")
    public void forgotPasswordCheckOTPAccepted() throws Exception {
        register = dataProvider.forgotPasswordExistUser();

        mockMvc.perform(post("/api/v1/register/forgotPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        register = dataProvider.forgotPasswordWithValidOTP();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/forgotPassword/checkOTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk())
                .andReturn();

        RegisterDTO response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), RegisterDTO.class);

        assertThat(response.getStatus()).isEqualTo("OTPAccepted");
    }


    @Test
    @Transactional
    @DisplayName("REST Request to forgot password Not Accepted")
    public void forgotPasswordCheckOTPNotAccepted() throws Exception {
        register = dataProvider.forgotPasswordExistUser();

        mockMvc.perform(post("/api/v1/register/forgotPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        register = dataProvider.forgotPasswordWithInValidOTP();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/forgotPassword/checkOTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        RegisterDTO response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), RegisterDTO.class);

        assertThat(response.getStatus()).isEqualTo("OTPNotAccepted");
    }

    @Test
    @Transactional
    @DisplayName("REST Request to forgot password Accepted")
    public void forgotPasswordUpdatePassword() throws Exception {
        register = dataProvider.forgotPasswordExistUser();

        mockMvc.perform(post("/api/v1/register/forgotPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        register = dataProvider.forgotPasswordWithValidOTP();

        mockMvc.perform(post("/api/v1/register/forgotPassword/checkOTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/register/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isAccepted())
                .andReturn();

        RegisterDTO response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), RegisterDTO.class);

        assertThat(response.getStatus()).isEqualTo("passwordUpdated");
    }
}
