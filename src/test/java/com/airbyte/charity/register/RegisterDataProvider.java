package com.airbyte.charity.register;

import com.airbyte.charity.dto.RegisterDTO;
import org.springframework.stereotype.Component;

import static com.airbyte.charity.CommonTestData.*;
import static com.airbyte.charity.register.OTPDatabase.OTP_MAP;

@Component
public class RegisterDataProvider {

    public RegisterDTO provideRegisterDTOForExistUser() {
        RegisterDTO dto = new RegisterDTO();
        dto.setPhoneNumber(DEFAULT_USERNAME);
        return dto;
    }

    public RegisterDTO provideRegisterDTOForNewUser() {
        RegisterDTO dto = new RegisterDTO();
        dto.setPhoneNumber(DEFAULT_MOBILE_NUMBER);
        return dto;
    }

    public RegisterDTO provideRegisterDTOWhenPhoneNumberIsEmpty() {
        RegisterDTO dto = new RegisterDTO();
        return dto;
    }

    public RegisterDTO provideRegisterDTOCheckOTP() {
        RegisterDTO dto = new RegisterDTO();
        dto.setPhoneNumber(DEFAULT_MOBILE_NUMBER);
        dto.setOtp(OTP_MAP.get(DEFAULT_MOBILE_NUMBER));
        if (dto.getOtp() == null || dto.getOtp().isEmpty()) {
            dto.setOtp("123456");
        }
        return dto;
    }

    public RegisterDTO provideRegisterDTOCheckOTPInvalid() {
        RegisterDTO dto = new RegisterDTO();
        dto.setPhoneNumber(DEFAULT_MOBILE_NUMBER);
        dto.setOtp("aaaaa");
        return dto;
    }

    public RegisterDTO createUser() {
        RegisterDTO dto = new RegisterDTO();
        dto.setPhoneNumber(DEFAULT_MOBILE_NUMBER);
        dto.setPassword(DEFAULT_STRING);
        dto.setLastName(DEFAULT_STRING);
        dto.setFirstName(DEFAULT_STRING);
        return dto;
    }

    public RegisterDTO forgotPasswordNewUser() {
        RegisterDTO dto = new RegisterDTO();
        dto.setPhoneNumber(DEFAULT_MOBILE_NUMBER);
        return dto;
    }

    public RegisterDTO forgotPasswordExistUser() {
        RegisterDTO dto = new RegisterDTO();
        dto.setPhoneNumber(DEFAULT_USERNAME);
        dto.setOtp(DEFAULT_STRING);
        return dto;
    }

    public RegisterDTO forgotPasswordWithValidOTP() {
        RegisterDTO dto = new RegisterDTO();
        dto.setPhoneNumber(DEFAULT_USERNAME);
        dto.setOtp(OTP_MAP.get(DEFAULT_USERNAME));
        return dto;
    }

    public RegisterDTO forgotPasswordWithInValidOTP() {
        RegisterDTO dto = new RegisterDTO();
        dto.setPhoneNumber(DEFAULT_USERNAME);
        dto.setOtp("123456");
        return dto;
    }
}
