package com.airbyte.charity.register;

import com.airbyte.charity.dto.RegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
@CrossOrigin("*")
public class RegisterController {
    private final RegisterService service;

    public RegisterController(RegisterService service) {
        this.service = service;
    }

    @PostMapping("/verify")
    public ResponseEntity<RegisterDTO> firstStep(@RequestBody RegisterDTO dto) {
        return new ResponseEntity<>(service.find(dto), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<RegisterDTO> secondStep(@RequestBody RegisterDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }
}
