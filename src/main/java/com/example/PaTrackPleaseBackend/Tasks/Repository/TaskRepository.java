package com.example.PaTrackPleaseBackend.Tasks.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PaTrackPleaseBackend.Tasks.Model.Tasks;
import java.util.List;

public interface TaskRepository extends JpaRepository<Tasks, Long> {
// This finds all tasks linked to a specific User's ID (the FK)

    List<Tasks> findByUserId(Long userId);

    // This is useful for fetching tasks via the email stored in React localStorage
    List<Tasks> findByUserEmail(String email);

}
