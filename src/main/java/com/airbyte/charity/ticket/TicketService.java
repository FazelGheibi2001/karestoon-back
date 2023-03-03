package com.airbyte.charity.ticket;

import com.airbyte.charity.common.ParentService;
import com.airbyte.charity.dto.ChatDTO;
import com.airbyte.charity.dto.TicketDTO;
import com.airbyte.charity.model.Chat;
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
    private final ChatRepository chatRepository;

    public TicketService(TicketRepository repository, ChatRepository chatRepository) {
        super(repository);
        this.chatRepository = chatRepository;
    }

    @Override
    public Ticket updateModelFromDto(Ticket ticket, TicketDTO dto) {
        ticket.setTitle(dto.getTitle() != null ? dto.getTitle() : ticket.getTitle());
        ticket.setStatus(dto.getStatus() != null ? dto.getStatus() : ticket.getStatus());
        return ticket;
    }

    @Override
    public Ticket convertDTO(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setUserId(dto.getUserId());
        ticket.setTitle(dto.getTitle());
        ticket.setStatus(dto.getStatus());
        return ticket;
    }

    @Override
    protected void postSave(Ticket ticket, TicketDTO dto) {
        saveChat(ticket, dto);
    }

    @Override
    protected void postUpdate(Ticket ticket, TicketDTO dto) {
        saveChat(ticket, dto);
    }

    private void saveChat(Ticket ticket, TicketDTO dto) {
        if (dto.getChatList() != null && !dto.getChatList().isEmpty()) {
            for (ChatDTO entity : dto.getChatList()) {
                Chat chat = new Chat();
                chat.setTicket(ticket);
                chat.setSender(entity.getSender());
                chat.setMessage(entity.getMessage());
                ticket.getChatList().add(chatRepository.save(chat));
            }
        }
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
