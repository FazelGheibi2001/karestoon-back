package com.airbyte.charity.ticket;

import com.airbyte.charity.dto.TicketDTO;
import com.airbyte.charity.model.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.airbyte.charity.permission.ManagePermission.TICKET_READ;
import static com.airbyte.charity.permission.ManagePermission.TICKET_WRITE;

@RestController
@RequestMapping("/api/v1/ticket")
@CrossOrigin("*")
public class TicketController {
    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }


    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json")
    @PreAuthorize(TICKET_WRITE)
    public ResponseEntity<Ticket> save(@RequestBody @Valid TicketDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @PreAuthorize(TICKET_READ)
    public ResponseEntity<Ticket> getById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    @PreAuthorize(TICKET_READ)
    public ResponseEntity<List<Ticket>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @PreAuthorize(TICKET_WRITE)
    public ResponseEntity<Ticket> update(@PathVariable("id") String id, @RequestBody TicketDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @PreAuthorize(TICKET_WRITE)
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity<List<Ticket>> getWithSearch(TicketDTO search) {
        return new ResponseEntity<>(service.getWithSearch(search), HttpStatus.OK);
    }

}
