package com.airbyte.charity.user;

import com.airbyte.charity.dto.UserDTO;
import com.airbyte.charity.permission.Role;
import org.springframework.stereotype.Component;

import static com.airbyte.charity.CommonTestData.*;

@Component
public class UserInformationDataProvider {

    public UserDTO createEntity() {
        UserDTO dto = new UserDTO();
        dto.setRole(Role.ADMIN.name());
        dto.setPassword(DEFAULT_STRING);
        dto.setFirstName(DEFAULT_NAME);
        dto.setLastName(DEFAULT_NAME);
        dto.setUsername(DEFAULT_STRING);
        return dto;
    }

    public UserDTO updateEntity() {
        UserDTO dto = new UserDTO();
        dto.setRole(Role.ADMIN.name());
        dto.setPassword(UPDATED_STRING);
        dto.setFirstName(UPDATED_NAME);
        dto.setLastName(UPDATED_NAME);
        dto.setUsername(UPDATED_STRING);
        return dto;
    }
}
