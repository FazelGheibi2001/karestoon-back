package com.airbyte.charity.project;

import com.airbyte.charity.dto.ProjectDTO;
import com.airbyte.charity.model.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.airbyte.charity.permission.ManagePermission.*;

@RestController
@RequestMapping("/api/v1/project")
@CrossOrigin("*")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }


    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json")
    @PreAuthorize(PROJECT_WRITE)
    public ResponseEntity<Project> save(@RequestBody @Valid ProjectDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Project> getById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<Project>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @PreAuthorize(PROJECT_WRITE)
    public ResponseEntity<Project> update(@PathVariable("id") String id, @RequestBody ProjectDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @PreAuthorize(PROJECT_WRITE)
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity<List<Project>> getWithSearch(ProjectDTO search) {
        return new ResponseEntity<>(service.getWithSearch(search), HttpStatus.OK);
    }
}
