package com.example.PaTrackPleaseBackend.Auth.Login.Dto;

public class UserDataResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public UserDataResponse(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters and Setters are REQUIRED for JSON serialization
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}