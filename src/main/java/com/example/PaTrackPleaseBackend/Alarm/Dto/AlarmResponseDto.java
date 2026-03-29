package com.example.PaTrackPleaseBackend.Alarm.Dto;

import java.time.LocalDateTime;

public class AlarmResponseDto {

    private Long id;
    private String alarmName;
    private LocalDateTime alarmStart;
    private LocalDateTime alarmFinish;
    private boolean isActive;

    public AlarmResponseDto(Long id, String alarmName, LocalDateTime alarmStart, LocalDateTime alarmFinish, boolean isActive) {
        this.id = id;
        this.alarmName = alarmName;
        this.alarmStart = alarmStart;
        this.alarmFinish = alarmFinish;
        this.isActive = isActive;
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
}