package com.airbyte.charity.sms;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static com.airbyte.charity.sms.SMSPanelConfig.*;

@Component
public class SMSSender {

    private final RestTemplate restTemplate;

    public SMSSender(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendSMS(String receiverNumber, String note) {
        String url = String.format("https://5m5.ir/send_via_get/send_sms.php?username=%s&password=%s&sender_number=%s&receiver_number=%s&note=%s", USERNAME, PASSWORD, NUMBER_3000, receiverNumber, note);
        try {
            restTemplate.getForObject(url, String.class);
        } catch (Exception exp) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "sms service not available, try later");
        }

    }


}
