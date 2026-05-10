package com.example.PaTrackPleaseBackend.Tasks.Controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.PaTrackPleaseBackend.Tasks.Dto.TaskCreateDto;
import com.example.PaTrackPleaseBackend.Tasks.Dto.TaskResponseDto;
import com.example.PaTrackPleaseBackend.Tasks.Dto.TaskUpdateDto;
import com.example.PaTrackPleaseBackend.Tasks.Dto.DashboardMetricsDto;
import com.example.PaTrackPleaseBackend.Tasks.Model.Tasks;
import com.example.PaTrackPleaseBackend.Tasks.Repository.TaskRepository;
import com.example.PaTrackPleaseBackend.User.Model.User;
import com.example.PaTrackPleaseBackend.Tasks.Service.TaskService;
import com.example.PaTrackPleaseBackend.User.Repository.UserRepository;
import com.example.PaTrackPleaseBackend.User.Service.UserService;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    public enum TaskStatus {
        DONE,
        UPCOMING,
        OVERDUE,
        IN_PROGRESS,
        PENDING
    }

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createTask(
            @RequestParam("email") String email,
            @RequestBody TaskCreateDto dto) {
        try {
            User user = userService.getUserByEmail(email);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found");
            }

            Tasks task = new Tasks();
            task.setTaskName(dto.getTaskName());
            task.setTaskDescription(dto.getTaskDescription());
            task.setDueDate(dto.getDueDate());
            task.setStatus(dto.getStatus());
            task.setUser(user);

            Tasks created = taskService.createTask(task);
            return new ResponseEntity<>(created, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getTasks(
            @RequestParam(value = "email", required = false) String email) {

        List<Tasks> taskList;

        if (email != null && !email.isEmpty()) {
            taskList = taskService.getTasksByEmail(email);
        } else {
            taskList = taskService.getAllTasks();
        }

        LocalDate today = LocalDate.now();

        for (Tasks task : taskList) {
            if (task.getDueDate() != null &&
                    task.getDueDate().isBefore(today) &&
                    !"DONE".equalsIgnoreCase(task.getStatus())) {

                task.setStatus("OVERDUE");
                taskRepository.save(task);
            }
        }

        List<TaskResponseDto> tasks = taskList.stream()
                .map(task -> new TaskResponseDto(
                        task.getId(),
                        task.getTaskName(),
                        task.getTaskDescription(),
                        task.getDueDate(),
                        task.getStatus()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        Tasks task = taskService.getTaskById(id); // Use the getTaskById service method

        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found with id " + id);
        }

        return ResponseEntity.ok(new TaskResponseDto(
                task.getId(),
                task.getTaskName(),
                task.getTaskDescription(),
                task.getDueDate(),
                task.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok("Task deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting task");
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long id,
            @RequestBody TaskUpdateDto updateDto,
            @RequestParam String email) {

        Tasks updated = taskService.updateTask(updateDto, email);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Tasks task = taskRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Task not found"));

            task.setStatus(status);
            taskRepository.save(task);

            return ResponseEntity.ok("Status Updated");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/metrics")
    public ResponseEntity<DashboardMetricsDto> getDashboardMetrics(
            @RequestParam(value = "email") String email) {

        // 1. Fetch the tasks for this user
        List<Tasks> taskList = taskService.getTasksByEmail(email);

        // 2. Run the counting logic
        long completed = taskList.stream()
                .filter(t -> "DONE".equalsIgnoreCase(t.getStatus()))
                .count();

        long overdue = taskList.stream()
                .filter(t -> "OVERDUE".equalsIgnoreCase(t.getStatus()))
                .count();

        long total = taskList.size();

        // Pending = anything not Done or Overdue (Upcoming/In Progress)
        long pending = total - completed - overdue;

        // 3. Return the single Object (The Curly Braces {} logic)
        return ResponseEntity.ok(new DashboardMetricsDto(completed, pending, overdue, total));
    }
}
