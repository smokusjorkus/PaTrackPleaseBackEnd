package com.example.PaTrackPleaseBackend.Auth.Login.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.PaTrackPleaseBackend.Auth.Login.Dto.LoginRequest;
import com.example.PaTrackPleaseBackend.Auth.Login.Dto.LoginResponse;
import com.example.PaTrackPleaseBackend.Security.JwtUtil;
import com.example.PaTrackPleaseBackend.User.Model.User;
import com.example.PaTrackPleaseBackend.User.Repository.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil; // ← add this

    public LoginResponse loginUser(LoginRequest request) {
        // 1. Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null); // ← updated for Optional

        // 2. Check if user exists
        if (user == null) {
            return new LoginResponse("User does not exist.", null, null, null);
        }

        // 3. Match passwords using BCrypt
        boolean passwordMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword());

        if (!passwordMatch) {
            return new LoginResponse("Incorrect password.", null, null, null);
        }

        // 4. Generate JWT token using email as the principal
        String token = jwtUtil.generateToken(user.getEmail()); // ← add this

        // 5. Success
        return new LoginResponse("Logged In", user.getUsername(), user.getEmail(), token);
    }
}