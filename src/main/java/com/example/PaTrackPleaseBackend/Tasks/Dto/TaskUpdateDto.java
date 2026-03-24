package com.example.PaTrackPleaseBackend.Tasks.Dto;

import java.time.LocalDate;

public class TaskUpdateDto {
    private Long id;
    private String taskName;
    private String taskDescription;
    private LocalDate dueDate;
    private String status;

    public TaskUpdateDto(){}

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

    public Long getId() {
        return id;
    }


}

