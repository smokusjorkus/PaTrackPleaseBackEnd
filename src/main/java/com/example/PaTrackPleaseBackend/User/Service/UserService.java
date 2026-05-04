package com.example.PaTrackPleaseBackend.User.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.PaTrackPleaseBackend.User.Dto.UserUpdateDTO;
import com.example.PaTrackPleaseBackend.User.Model.User;
import com.example.PaTrackPleaseBackend.User.Repository.UserRepository;

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
        return userRepository.findByEmail(email).orElse(null);
    }

    // DELETE USER
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Method to handle the Photo Update logic specifically
    @Transactional
    public User updateProfilePhoto(String email, MultipartFile file) throws IOException {
        User existingUser = userRepository.findByEmail(email).orElse(null);

        if (existingUser == null) {
            throw new RuntimeException("User not found: " + email);
        }

        // DELETE LOGIC: If file is null/empty, clear the URL
        if (file == null || file.isEmpty()) {
            // Optional: You could delete the old file from the disk here if you wanted
            existingUser.setProfileImageUrl(null);
        } else {
            // UPDATE LOGIC: Save the file and update the path
            String imageUrl = saveProfileImage(file);
            existingUser.setProfileImageUrl(imageUrl);
        }

        return userRepository.save(existingUser);
    }

    // UPDATE USER
    @Transactional
    public User updateUser(String email, UserUpdateDTO updateDto) {
        User existingUser = userRepository.findByEmail(email).orElse(null);

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

    public String saveProfileImage(MultipartFile file) throws IOException {
        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + File.separator + "uploads" + File.separator + "profiles" + File.separator;

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            System.out.println("Directory created: " + created + " at " + uploadDir);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        System.out.println("Attempting to save file to: " + filePath.toAbsolutePath());

        try (var inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        System.out.println("File saved successfully!");

        return "/uploads/profiles/" + fileName;
    }
}