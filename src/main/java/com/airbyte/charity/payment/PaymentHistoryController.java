package com.airbyte.charity.payment;

import com.airbyte.charity.dto.PaymentHistoryDTO;
import com.airbyte.charity.model.PaymentHistory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.airbyte.charity.permission.ManagePermission.*;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin("*")
public class PaymentHistoryController {
    private final PaymentHistoryService service;

    public PaymentHistoryController(PaymentHistoryService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json")
    @PreAuthorize(PAYMENT_WRITE)
    public ResponseEntity<PaymentHistory> save(@RequestBody @Valid PaymentHistoryDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @PreAuthorize(PAYMENT_READ)
    public ResponseEntity<PaymentHistory> getById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    @PreAuthorize(PAYMENT_READ)
    public ResponseEntity<List<PaymentHistory>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @PreAuthorize(PAYMENT_WRITE)
    public ResponseEntity<PaymentHistory> update(@PathVariable("id") String id, @RequestBody PaymentHistoryDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @PreAuthorize(PAYMENT_WRITE)
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    @PreAuthorize(PAYMENT_READ)
    public ResponseEntity<List<PaymentHistory>> getWithSearch(PaymentHistoryDTO search) {
        return new ResponseEntity<>(service.getWithSearch(search), HttpStatus.OK);
    }
}
