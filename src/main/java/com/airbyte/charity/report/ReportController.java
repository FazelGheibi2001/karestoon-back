package com.airbyte.charity.report;

import com.airbyte.charity.dto.ReportDTO;
import com.airbyte.charity.model.Report;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
@CrossOrigin("*")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json")
    public ResponseEntity<Report> save(@RequestBody @Valid ReportDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Report> getById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<Report>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Report> update(@PathVariable("id") String id, @RequestBody ReportDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity<List<Report>> getWithSearch(ReportDTO search) {
        return new ResponseEntity<>(service.getWithSearch(search), HttpStatus.OK);
    }
}
