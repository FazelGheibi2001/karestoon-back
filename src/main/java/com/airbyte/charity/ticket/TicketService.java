package com.airbyte.charity.ticket;

import com.airbyte.charity.common.ParentService;
import com.airbyte.charity.dto.TicketDTO;
import com.airbyte.charity.model.Ticket;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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
        ticket.setTitle(dto.getTitle() != null ? dto.getTitle() : ticket.getTitle());
        return ticket;
    }
    @Override
    public Ticket convertDTO(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setRequest(dto.getRequest());
        ticket.setResponse(dto.getResponse());
        ticket.setSender(dto.getSender());
        ticket.setSenderProfile(dto.getSenderProfile());
        ticket.setUserId(dto.getUserId());
        ticket.setTitle(dto.getTitle());
        return ticket;
    }

    @Override
    public List<Ticket> getWithSearch(TicketDTO search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> criteriaBuilderQuery = criteriaBuilder.createQuery(Ticket.class);

        Root<Ticket> root = criteriaBuilderQuery.from(Ticket.class);
        List<Predicate> predicates = new ArrayList<>();

        if (search.getDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("date"), search.getDate()));
        }
        if (search.getUserId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("userId"), search.getUserId()));
        }

        criteriaBuilderQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaBuilderQuery).getResultList();
    }
}
