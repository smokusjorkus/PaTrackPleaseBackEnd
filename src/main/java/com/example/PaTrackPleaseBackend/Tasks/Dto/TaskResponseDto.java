package com.example.PaTrackPleaseBackend.Tasks.Dto;

import java.time.LocalDate;

public class TaskResponseDto {

    private Long id;
    private String taskName;
    private String taskDescription;
    private LocalDate dueDate;
    private String status;

    public TaskResponseDto(Long id, String taskName, String taskDescription, LocalDate dueDate, String status) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
        this.status = status;
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
        return status;
    }

}
