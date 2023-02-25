package com.airbyte.charity;

import com.airbyte.charity.dto.UserDTO;
import com.airbyte.charity.permission.Role;
import com.airbyte.charity.user.UserInformationService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserInformationService userInformationService;

    public DataLoader(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            userInformationService.getByUsername("09924664362");
        }
        catch (IllegalArgumentException usernameNotFoundException) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("09924664362");
            userDTO.setPassword("airByte");
            userDTO.setRole(Role.MANAGER.name());
            userInformationService.save(userDTO);
        }
    }
}
