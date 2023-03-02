package com.airbyte.charity.user;

import com.airbyte.charity.dto.UserDTO;
import com.airbyte.charity.model.UserInformation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.airbyte.charity.permission.ManagePermission.*;

@RestController
@RequestMapping(value = "/api/v1/user")
@CrossOrigin("*")
public class UserInformationController {
    private final UserInformationService service;

    public UserInformationController(UserInformationService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize(USER_WRITE)
    public ResponseEntity<List<UserInformation>> findAllUsers() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize(USER_READ)
    public ResponseEntity<UserInformation> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize(USER_WRITE)
    public ResponseEntity<UserInformation> saveInformation(@RequestBody UserDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize(USER_WRITE)
    public ResponseEntity<Void> deleteInformation(@PathVariable("id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @PreAuthorize(USER_UPDATE)
    public ResponseEntity<UserInformation> update(@PathVariable("id") String id, @RequestBody UserDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.ACCEPTED);
    }

}
