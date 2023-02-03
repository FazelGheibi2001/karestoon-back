package com.airbyte.charity.payment;

import com.airbyte.charity.dto.PaymentHistoryDTO;
import com.airbyte.charity.model.PaymentHistory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin("*")
public class PaymentHistoryController {
    private final PaymentHistoryService service;

    public PaymentHistoryController(PaymentHistoryService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json")
    public ResponseEntity<PaymentHistory> save(@RequestBody @Valid PaymentHistoryDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<PaymentHistory> getById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<PaymentHistory>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<PaymentHistory> update(@PathVariable("id") String id, @RequestBody PaymentHistoryDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity<List<PaymentHistory>> getWithSearch(PaymentHistoryDTO search) {
        return new ResponseEntity<>(service.getWithSearch(search), HttpStatus.OK);
    }
}
