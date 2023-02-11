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

    @PostMapping("/verify/checkOTP")
    public ResponseEntity<Void> checkOtp(@RequestBody RegisterDTO dto) {
        Boolean isValid = service.checkOTP(dto);
        if (isValid) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<RegisterDTO> secondStep(@RequestBody RegisterDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<RegisterDTO> forgotPassword(@RequestBody RegisterDTO dto) {
        return new ResponseEntity<>(service.forgotPassword(dto), HttpStatus.OK);
    }

    @PostMapping("/forgotPassword/checkOTP")
    public ResponseEntity<RegisterDTO> forgotPasswordCheckOTP(@RequestBody RegisterDTO dto) {
        return new ResponseEntity<>(service.checkOTPForgotPassword(dto), HttpStatus.OK);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<RegisterDTO> updatePassword(@RequestBody RegisterDTO dto) {
        return new ResponseEntity<>(service.updatePassword(dto), HttpStatus.ACCEPTED);
    }
}
