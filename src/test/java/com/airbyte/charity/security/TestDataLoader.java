package com.airbyte.charity.security;

import com.airbyte.charity.model.UserInformation;
import com.airbyte.charity.permission.Role;
import com.airbyte.charity.user.UserInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static com.airbyte.charity.CommonTestData.*;

@Component
public class TestDataLoader implements ApplicationRunner {

    private UserInformationRepository userRepository;

    @Autowired
    public TestDataLoader(UserInformationRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
        UserInformation userInformation = new UserInformation();
        userInformation.setPassword(DEFAULT_PASSWORD);
        userInformation.setUsername(DEFAULT_USERNAME);
        userInformation.setFirstName(DEFAULT_NAME);
        userInformation.setLastName(DEFAULT_STRING);
        userInformation.setRole(Role.MANAGER);
        userRepository.save(userInformation);
    }
}
