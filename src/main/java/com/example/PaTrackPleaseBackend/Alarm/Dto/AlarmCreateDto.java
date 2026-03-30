package com.example.PaTrackPleaseBackend.Alarm.Dto;

import java.time.LocalDateTime;

public class AlarmCreateDto {
    private String alarmName;
    private LocalDateTime alarmStart;
    private LocalDateTime alarmFinish;
    private Long taskId;

    public AlarmCreateDto() {
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public LocalDateTime getAlarmStart() {
        return alarmStart;
    }

    public void setAlarmStart(LocalDateTime alarmStart) {
        this.alarmStart = alarmStart;
    }

    public LocalDateTime getAlarmFinish() {
        return alarmFinish;
    }

    public void setAlarmFinish(LocalDateTime alarmFinish) {
        this.alarmFinish = alarmFinish;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}