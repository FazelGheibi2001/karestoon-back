package com.airbyte.charity.ticket;

import com.airbyte.charity.dto.ChatDTO;
import com.airbyte.charity.dto.TicketDTO;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.airbyte.charity.CommonTestData.*;
import static com.airbyte.charity.CommonTestData.UPDATED_NAME;

@Component
public class TicketDataProvider {

    public TicketDTO createEntity() {
        TicketDTO dto = new TicketDTO();
        dto.setTitle(DEFAULT_STRING);
        dto.setDate(DEFAULT_DATE);
        dto.setStatus(DEFAULT_STRING);
        dto.setUserId(DEFAULT_ID);
        dto.setChatList(List.of(prepareChat()));
        return dto;
    }

    public TicketDTO updateEntity() {
        TicketDTO dto = new TicketDTO();
        dto.setTitle(UPDATED_STRING);
        dto.setDate(UPDATED_DATE);
        dto.setStatus(UPDATED_STRING);
        dto.setUserId(UPDATED_ID);
        dto.setChatList(List.of(prepareChatAnother()));
        return dto;
    }

    public ChatDTO prepareChat() {
        ChatDTO dto = new ChatDTO();
        dto.setDate(DEFAULT_DATE);
        dto.setMessage(DEFAULT_STRING);
        dto.setSender(DEFAULT_NAME);
        return dto;
    }

    public ChatDTO prepareChatAnother() {
        ChatDTO dto = new ChatDTO();
        dto.setDate(UPDATED_DATE);
        dto.setMessage(UPDATED_STRING);
        dto.setSender(UPDATED_NAME);
        return dto;
    }
}
