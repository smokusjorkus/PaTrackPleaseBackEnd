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

    User user = userRepository.findByEmail(request.getEmail());

    if (user == null) {
        return new LoginResponse("User does not exist.", null, null);
    }

    boolean passwordMatch = passwordEncoder.matches(
            request.getPassword(),
            user.getPassword()
    );

    if (!passwordMatch) {
        return new LoginResponse("Incorrect password.", null, null);
    }

    return new LoginResponse("Logged In", user.getUsername(), user.getEmail());
}
}