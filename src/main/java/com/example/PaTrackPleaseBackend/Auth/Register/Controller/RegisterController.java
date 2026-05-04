package com.example.PaTrackPleaseBackend.Auth.Register.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.PaTrackPleaseBackend.Auth.Register.Dto.RegisterRequest;
import com.example.PaTrackPleaseBackend.Auth.Register.Dto.RegisterResponse;
import com.example.PaTrackPleaseBackend.Auth.Register.Service.RegistrationService;

@RestController
@RequestMapping("/api/register")

public class RegisterController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = registrationService.registerUser(request);
        return ResponseEntity.ok(response);
    }
}