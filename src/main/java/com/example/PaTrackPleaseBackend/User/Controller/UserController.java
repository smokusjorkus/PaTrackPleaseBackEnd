package com.example.PaTrackPleaseBackend.User.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.PaTrackPleaseBackend.User.Model.User;
import com.example.PaTrackPleaseBackend.User.Service.UserService;
import com.example.PaTrackPleaseBackend.User.Dto.UserUpdateDTO;
import com.example.PaTrackPleaseBackend.User.Dto.UserResponseDto;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE USER
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    // GET ALL USERS
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers().stream()
                .map(user -> new UserResponseDto(
                    user.getId(), 
                    user.getUsername(), 
                    user.getEmail(), 
                    user.getProfileImageUrl()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // GET USER BY EMAIL
    @GetMapping("/email")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with email: " + email);
        }
        return ResponseEntity.ok(new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileImageUrl()
        ));
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id: " + id);
        }
        return ResponseEntity.ok(new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileImageUrl()
        ));
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }

    // UPDATE PROFILE (Username, Email, etc.)
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(
            @RequestParam("email") String email, 
            @RequestBody UserUpdateDTO updateDto 
    ) {
        try {
            User updatedUser = userService.updateUser(email, updateDto);
            return ResponseEntity.ok(new UserResponseDto(
                    updatedUser.getId(),
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),
                    updatedUser.getProfileImageUrl()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Backend Error: " + e.getMessage());
        }
    }

    // UPLOAD PHOTO
    @PostMapping("/upload-photo")
    public ResponseEntity<?> uploadPhoto(
            @RequestParam("email") String email, 
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            User updatedUser = userService.updateProfilePhoto(email, file);
            // Return DTO so React gets the profileImageUrl field immediately
            return ResponseEntity.ok(new UserResponseDto(
                    updatedUser.getId(),
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),
                    updatedUser.getProfileImageUrl()
            ));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload photo");
        }
    }
}