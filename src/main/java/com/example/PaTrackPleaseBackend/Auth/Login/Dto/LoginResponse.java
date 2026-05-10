package com.example.PaTrackPleaseBackend.Auth.Login.Dto;

public class LoginResponse {
    private String message;
    private String token;
    private UserDataResponse user; // This creates the "user": { ... } block in JSON

    public LoginResponse(String message, String token, UserDataResponse user) {
        this.message = message;
        this.token = token;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public UserDataResponse getUser() {
        return user;
    }
}