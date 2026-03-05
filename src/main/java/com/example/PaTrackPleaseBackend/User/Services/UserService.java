package com.example.PaTrackPleaseBackend.User.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PaTrackPleaseBackend.User.Repository.UserRepository;
import com.example.PaTrackPleaseBackend.User.Model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // CREATE USER
    public User createUser(User user){
        return userRepository.save(user);
    }

    // GET ALL USERS
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // GET USER BY ID
    public User getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    // DELETE USER
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}