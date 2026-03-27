package com.example.PaTrackPleaseBackend.Auth.Login.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.PaTrackPleaseBackend.Auth.Login.Dto.LoginRequest;
import com.example.PaTrackPleaseBackend.Auth.Login.Dto.LoginResponse;
import com.example.PaTrackPleaseBackend.User.Model.User;
import com.example.PaTrackPleaseBackend.User.Repository.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse loginUser(LoginRequest request) {
        // 1. Find user by email
        User user = userRepository.findByEmail(request.getEmail());

        // 2. Check if user exists
        if (user == null) {
            return new LoginResponse("User does not exist.", null, null);
        }

        // 3. Match passwords using BCrypt
        boolean passwordMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatch) {
            return new LoginResponse("Incorrect password.", null, null);
        }

        // 4. Success: Return response with username and email
        return new LoginResponse("Logged In", user.getUsername(), user.getEmail());
    }
}
