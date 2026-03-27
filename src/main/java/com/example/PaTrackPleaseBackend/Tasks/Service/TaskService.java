package com.example.PaTrackPleaseBackend.Tasks.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Crucial for Spring to recognize this class
import com.example.PaTrackPleaseBackend.Tasks.Dto.TaskUpdateDto;
import com.example.PaTrackPleaseBackend.Tasks.Model.Tasks;
import com.example.PaTrackPleaseBackend.Tasks.Repository.TaskRepository;
import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public Tasks createTask(Tasks task) {
        return taskRepository.save(task);
    }

    public Tasks getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // READ ALL
    public List<Tasks> getAllTasks() {
        return taskRepository.findAll();
    }

    // READ BY EMAIL (For React Dashboard)
    public List<Tasks> getTasksByEmail(String email) {
        return taskRepository.findByUserEmail(email);
    }

    // DELETE
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public Tasks updateTask(TaskUpdateDto updateDto, String currentUserEmail) {
        // 1. Find the task by ID from the DTO
        Tasks existingTask = taskRepository.findById(updateDto.getId())
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + updateDto.getId()));

        // 2. VERIFICATION: Ensure the task belongs to the user trying to edit it
        // We use .getUser() because that is the name of the field in your Tasks model
        if (!existingTask.getUser().getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Unauthorized: You do not own this task.");
        }

        // 3. Update the fields
        existingTask.setTaskName(updateDto.getTaskName());
        existingTask.setTaskDescription(updateDto.getTaskDescription());
        existingTask.setDueDate(updateDto.getDueDate());
        existingTask.setStatus(updateDto.getStatus());

        // 4. Save and return the updated task
        return taskRepository.save(existingTask);
    }
}
