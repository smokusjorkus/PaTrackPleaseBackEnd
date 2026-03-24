package com.example.PaTrackPleaseBackend.Tasks.Dto;

import java.time.LocalDate;

public class TaskResponseDto {

    private Long id;
    private String taskName;
    private String taskDescription;
    private LocalDate dueDate;
    private String Status;

public TaskResponseDto(Long id, String taskName, String taskDescription, LocalDate dueDate, String status){
    this.id = id;
    this.taskName = taskName; // ADD THIS LINE - it was missing!
    this.taskDescription = taskDescription;
    this.dueDate = dueDate;
    this.Status = status;
}

    public Long getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return Status;
    }

    
}
