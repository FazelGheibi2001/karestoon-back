package com.airbyte.charity.register;

import com.airbyte.charity.dto.RegisterDTO;
import org.springframework.stereotype.Component;

import static com.airbyte.charity.CommonTestData.*;

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
}
