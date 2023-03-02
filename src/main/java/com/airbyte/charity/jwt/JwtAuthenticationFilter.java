package com.airbyte.charity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

import static com.airbyte.charity.jwt.JwtConfig.SECURE_KEY;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDTO usernameAndPasswordDTO = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginDTO.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    usernameAndPasswordDTO.getUsername(),
                    usernameAndPasswordDTO.getPassword()
            );

            return authenticationManager.authenticate(authentication);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String role = "";
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        for (int index = 0; index < authorities.size(); index++) {
            String value = authorities.iterator().next().getAuthority();
            if (value.contains("Role: ")) {
                role = value.replace("Role: ", "");
                break;
            }
        }

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("role", role)
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(30 * 60)))
                .signWith(Keys.hmacShaKeyFor(SECURE_KEY.getBytes()))
                .compact();

        response.addHeader("Authorization", token);
    }
}
