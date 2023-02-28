package com.airbyte.charity.payment;

import com.airbyte.charity.dto.PaymentHistoryDTO;
import org.springframework.stereotype.Component;

import static com.airbyte.charity.CommonTestData.*;

@Component
public class PaymentDataProvider {

    public PaymentHistoryDTO createEntity() {
        PaymentHistoryDTO dto = new PaymentHistoryDTO();
        dto.setAmount(DEFAULT_STRING);
        dto.setUsername(DEFAULT_USERNAME);
        dto.setProjectId(DEFAULT_ID);
        dto.setProjectName(DEFAULT_STRING);
        return dto;
    }

    public PaymentHistoryDTO updateEntity() {
        PaymentHistoryDTO dto = new PaymentHistoryDTO();
        dto.setAmount(UPDATED_STRING);
        dto.setUsername(DEFAULT_USERNAME);
        dto.setProjectId(UPDATED_ID);
        dto.setProjectName(UPDATED_STRING);
        return dto;
    }
}
