package com.airbyte.charity.payment;

import com.airbyte.charity.dto.PaymentHistoryDTO;
import org.springframework.stereotype.Component;

import static com.airbyte.charity.CommonTestData.*;

@Component
public class PaymentDataProvider {

    public PaymentHistoryDTO createEntity() {
        PaymentHistoryDTO dto = new PaymentHistoryDTO();
        dto.setAmount(DEFAULT_STRING);
        dto.setUserId(DEFAULT_ID);
        dto.setProjectId(DEFAULT_ID);
        dto.setProjectName(DEFAULT_STRING);
        return dto;
    }

    public PaymentHistoryDTO updateEntity() {
        PaymentHistoryDTO dto = new PaymentHistoryDTO();
        dto.setAmount(UPDATED_STRING);
        dto.setUserId(UPDATED_ID);
        dto.setProjectId(UPDATED_ID);
        dto.setProjectName(UPDATED_STRING);
        return dto;
    }
}
