package com.airbyte.charity.ticket;

import com.airbyte.charity.common.ParentService;
import com.airbyte.charity.dto.TicketDTO;
import com.airbyte.charity.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService extends ParentService<Ticket, TicketRepository, TicketDTO> {

    public TicketService(TicketRepository repository) {
        super(repository);
    }

    @Override
    public Ticket updateModelFromDto(Ticket ticket, TicketDTO dto) {
        ticket.setRequest(dto.getRequest() != null ? dto.getRequest() : ticket.getRequest());
        ticket.setResponse(dto.getResponse() != null ? dto.getResponse() : ticket.getResponse());
        return ticket;
    }

    @Override
    public Ticket convertDTO(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setRequest(dto.getRequest());
        ticket.setResponse(dto.getResponse());
        ticket.setSender(dto.getSender());
        ticket.setSenderProfile(dto.getSenderProfile());
        return ticket;
    }

    @Override
    public List<Ticket> getWithSearch(TicketDTO search) {
        return null;
    }
}
