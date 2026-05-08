package com.example.PaTrackPleaseBackend.Auth.Register.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.PaTrackPleaseBackend.Auth.Register.Dto.RegisterRequest;
import com.example.PaTrackPleaseBackend.Auth.Register.Dto.RegisterResponse;
import com.example.PaTrackPleaseBackend.Security.SecurityConfig;
import com.example.PaTrackPleaseBackend.User.Model.User;
import com.example.PaTrackPleaseBackend.User.Repository.UserRepository;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterResponse registerUser(RegisterRequest request) {

        // if(!request.getPassword().equals(request.getConfirmPassword())) {
        // throw new RuntimeException("Passwords do not match");
        // }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();s
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return new RegisterResponse(
                "User registered successfully",
                user.getId(),
                user.getUsername(),
                user.getEmail());
    }
}