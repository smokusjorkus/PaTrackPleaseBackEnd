package com.example.PaTrackPleaseBackend.Tasks.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class TaskUpdateDto {

    private Long id;
    private String taskName;
    private String taskDescription;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private String status;

    public TaskUpdateDto() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}