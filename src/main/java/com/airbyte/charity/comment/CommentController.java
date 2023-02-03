package com.airbyte.charity.comment;

import com.airbyte.charity.dto.CommentDTO;
import com.airbyte.charity.model.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/comment")
@CrossOrigin("*")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json")
    public ResponseEntity<Comment> save(@RequestBody @Valid CommentDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Comment> getById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<Comment>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Comment> update(@PathVariable("id") String id, @RequestBody CommentDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity<List<Comment>> getWithSearch(CommentDTO search) {
        return new ResponseEntity<>(service.getWithSearch(search), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}")
    public ResponseEntity<List<Comment>> get(@PathVariable("projectId") String projectId) {
        return new ResponseEntity<>(service.getGoodComment(projectId), HttpStatus.OK);
    }
}
