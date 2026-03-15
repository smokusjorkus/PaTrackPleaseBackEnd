package com.example.PaTrackPleaseBackend.User.Service;

import com.example.PaTrackPleaseBackend.User.Dto.UserUpdateDTO;
import com.example.PaTrackPleaseBackend.User.Model.User;
import com.example.PaTrackPleaseBackend.User.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    // CREATE USER
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET USER BY ID
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    // GET USER BY EMAIL
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // DELETE USER
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // UPDATE USER
    @Transactional
    public User updateUser(String email, UserUpdateDTO updateDto) {
        User existingUser = userRepository.findByEmail(email);
        
        if (existingUser == null) {
            throw new RuntimeException("User not found: " + email);
        }

        // 2. Perform safe, conditional field mapping from DTO to Entity
        
        // Update Username
        if (updateDto.getUsername() != null && !updateDto.getUsername().isEmpty()) {
            existingUser.setUsername(updateDto.getUsername());
        }

        // Update Email
        if (updateDto.getEmail() != null && !updateDto.getEmail().isEmpty()) {
            existingUser.setEmail(updateDto.getEmail());
        }

        // Update Profile Image URL
        if (updateDto.getProfileImageUrl() != null && !updateDto.getProfileImageUrl().isEmpty()) {
            existingUser.setProfileImageUrl(updateDto.getProfileImageUrl());
        }

        // Update Password
        if (updateDto.getPassword() != null && !updateDto.getPassword().isEmpty()) {
            // This line does the magic!
            String hashedEncodedPassword = passwordEncoder.encode(updateDto.getPassword());
            existingUser.setPassword(hashedEncodedPassword);
        }

        // 3. Save the modified entity
        // Since the ID already exists, JPA will perform an UPDATE statement
        return userRepository.save(existingUser);
    }
}