package com.example.PaTrackPleaseBackend.Auth.Logout.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class LogoutController {

    @PostMapping("/logout")
    public String Logout() {
        return  "Logout Successful";
    }
    
}
