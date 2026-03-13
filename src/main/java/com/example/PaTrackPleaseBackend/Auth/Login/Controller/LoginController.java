package com.example.PaTrackPleaseBackend.Auth.Login.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.PaTrackPleaseBackend.Auth.Login.Dto.LoginRequest;
import com.example.PaTrackPleaseBackend.Auth.Login.Dto.LoginResponse;
import com.example.PaTrackPleaseBackend.Auth.Login.Service.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        LoginResponse response = loginService.loginUser(request);

        return ResponseEntity.ok(response);
    }
}