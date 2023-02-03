package com.airbyte.charity.auth;

import com.airbyte.charity.model.UserInformation;
import com.airbyte.charity.user.UserInformationService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserInformationService userInformationService;

    public ApplicationUserService(PasswordEncoder passwordEncoder,
                                  UserInformationService userInformationService) {
        this.passwordEncoder = passwordEncoder;
        this.userInformationService = userInformationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInformation user = userInformationService.getByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("can not find user");
        }

        return new ApplicationUser(
                user.getRole().getGrantedAuthority(),
                passwordEncoder.encode(user.getPassword()),
                user.getUsername(),
                true,
                true,
                true,
                true
        );
    }
}
