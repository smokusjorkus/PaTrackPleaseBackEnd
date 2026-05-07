package com.example.PaTrackPleaseBackend.Auth.Register.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.PaTrackPleaseBackend.Auth.Register.Dto.RegisterRequest;
import com.example.PaTrackPleaseBackend.Auth.Register.Dto.RegisterResponse;
import com.example.PaTrackPleaseBackend.Auth.Register.Service.RegistrationService;

@RestController
@RequestMapping("/api/auth/register")
public class RegisterController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = registrationService.registerUser(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}