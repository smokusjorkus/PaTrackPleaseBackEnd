package com.example.PaTrackPleaseBackend.User.Dto;

public class UserResponseDto {

    private String username;
    private Long id;
    private String email;

    public UserResponseDto(Long id, String username, String email) {
        this.username = username;
        this.id = id;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}