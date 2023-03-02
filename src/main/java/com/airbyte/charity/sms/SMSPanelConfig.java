package com.airbyte.charity.sms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SMSPanelConfig {
    public static String NUMBER_50004 = "50004044366666";
    public static String NUMBER_3000 = "30008907";
    public static String NUMBER_10000000000004 = "10000000000004";
    public static String NUMBER_982122419442 = "982122419442";
    public static String USERNAME = "salamco";
    public static String PASSWORD = "100200";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
