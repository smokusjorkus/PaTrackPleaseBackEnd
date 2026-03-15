package com.example.PaTrackPleaseBackend.Auth.Login.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.PaTrackPleaseBackend.Auth.Login.Dto.LoginRequest;
import com.example.PaTrackPleaseBackend.Auth.Login.Dto.LoginResponse;
import com.example.PaTrackPleaseBackend.Auth.Login.Service.LoginService;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    // 1. Get the response object from the service
    LoginResponse response = loginService.loginUser(request);

    // 2. Check if the login was actually successful based on your service message
    if ("Logged In".equals(response.getMessage())) {
        return ResponseEntity.ok(response); // Returns 200 OK
    }

    // 3. If it wasn't "Logged In", it's a 401 Unauthorized
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    
}