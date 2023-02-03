package com.airbyte.charity.register;

import com.airbyte.charity.dto.RegisterDTO;
import com.airbyte.charity.dto.UserDTO;
import com.airbyte.charity.model.UserInformation;
import com.airbyte.charity.permission.Role;
import com.airbyte.charity.user.UserInformationService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RegisterService {
    private final UserInformationService userInformationService;

    public RegisterService(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    public RegisterDTO find(RegisterDTO dto) {
        try {
            UserInformation information = userInformationService.getByUsername(dto.getPhoneNumber());
            dto.setRole(Role.USER.name());
            dto.setPassword(information.getPassword());
            dto.setFirstName(information.getFirstName());
            dto.setLastName(information.getLastName());
            dto.setStatus("exist");
            return dto;
        } catch (IllegalArgumentException exception) {
            String otp = generateOTP(6);
            dto.setOtp(otp);
            dto.setStatus("newUser");
            return dto;
        }
    }

    private String generateOTP(int length) {
        String otp = "";
        for (int index = 0; index < length; index++) {
            Random random = new Random();
            int randomInt = random.nextInt(10);
            otp += (char) ('0' + randomInt);
        }
        return otp;
    }

    public RegisterDTO create(RegisterDTO dto) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(dto.getPhoneNumber());
        userDTO.setPassword(dto.getPassword());
        userDTO.setFirstName(dto.getFirstName());
        userDTO.setLastName(dto.getLastName());
        userDTO.setRole(Role.USER.name());
        userInformationService.save(userDTO);
        dto.setStatus("create");
        dto.setRole(Role.USER.name());
        return dto;
    }
}
