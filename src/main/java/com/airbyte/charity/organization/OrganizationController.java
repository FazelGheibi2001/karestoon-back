package com.airbyte.charity.organization;

import com.airbyte.charity.dto.OrganizationDTO;
import com.airbyte.charity.model.Organization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.airbyte.charity.permission.ManagePermission.ORGANIZATION_WRITE;

@RestController
@RequestMapping("/api/v1/organization")
@CrossOrigin("*")
public class OrganizationController {
    private final OrganizationService service;

    public OrganizationController(OrganizationService service) {
        this.service = service;
    }


    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json")
    @PreAuthorize(ORGANIZATION_WRITE)
    public ResponseEntity<Organization> save(@RequestBody @Valid OrganizationDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Organization> getById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<Organization>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @PreAuthorize(ORGANIZATION_WRITE)
    public ResponseEntity<Organization> update(@PathVariable("id") String id, @RequestBody OrganizationDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @PreAuthorize(ORGANIZATION_WRITE)
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity<List<Organization>> getWithSearch(OrganizationDTO search) {
        return new ResponseEntity<>(service.getWithSearch(search), HttpStatus.OK);
    }
}
