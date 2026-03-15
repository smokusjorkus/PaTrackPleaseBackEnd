package com.example.PaTrackPleaseBackend.User.Dto;

public class UserUpdateDTO {
    private String username;
    private String email;
    private String password;
    private String profileImageUrl;

    // Default constructor is required for Jackson
    public UserUpdateDTO() {}

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
}