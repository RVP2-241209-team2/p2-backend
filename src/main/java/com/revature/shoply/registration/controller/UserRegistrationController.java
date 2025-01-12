package com.revature.shoply.registration.controller;

import com.revature.shoply.registration.dto.UserRegistrationRequestDTO;
import com.revature.shoply.registration.dto.UserRegistrationResponseDTO;
import com.revature.shoply.registration.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/users")
public class UserRegistrationController {

    private final UserRegistrationService registrationService;

    public UserRegistrationController(UserRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/v1/register")
    public ResponseEntity<UserRegistrationResponseDTO> registerUser(@RequestBody UserRegistrationRequestDTO registrationDto) {
        UserRegistrationResponseDTO responseDto = registrationService.registerUser(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
