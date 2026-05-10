package com.example.PaTrackPleaseBackend.Auth.Login.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.PaTrackPleaseBackend.Auth.Login.Dto.LoginRequest;
import com.example.PaTrackPleaseBackend.Auth.Login.Dto.LoginResponse;
import com.example.PaTrackPleaseBackend.Auth.Login.Dto.UserDataResponse;
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
        // 1. Find user
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            return new LoginResponse("User does not exist.", null, null);
        }

        // 2. Validate Password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse("Incorrect password.", null, null);
        }

        // 3. Generate Token
        String token = jwtUtil.generateToken(user.getEmail());

        // 4. Wrap User Data for the nested JSON structure
        UserDataResponse userData = new UserDataResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());

        // 5. Return the full synchronized response
        return new LoginResponse("Logged In", token, userData);
    }
}