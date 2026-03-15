package com.example.PaTrackPleaseBackend.User.Dto;

public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private String profileImageUrl; // ADDED: Now the backend can "see" this field

    // Updated Constructor
    public UserResponseDto(Long id, String username, String email, String profileImageUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getProfileImageUrl() { return profileImageUrl; }
}