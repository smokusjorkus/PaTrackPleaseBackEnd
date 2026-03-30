package com.example.PaTrackPleaseBackend.Alarm.Dto;

import java.time.LocalDateTime;

public class AlarmResponseDto {

    private Long id;
    private String alarmName;
    private LocalDateTime alarmStart;
    private LocalDateTime alarmFinish;
    private boolean isActive;
    private Long taskId;

    public AlarmResponseDto(Long id, String alarmName, LocalDateTime alarmStart, LocalDateTime alarmFinish,
            boolean isActive, Long taskId) {
        this.id = id;
        this.alarmName = alarmName;
        this.alarmStart = alarmStart;
        this.alarmFinish = alarmFinish;
        this.isActive = isActive;
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public LocalDateTime getAlarmStart() {
        return alarmStart;
    }

    public LocalDateTime getAlarmFinish() {
        return alarmFinish;
    }

    public boolean isActive() {
        return isActive;
    }

    public Long getTaskId() {
        return taskId;
    }
}