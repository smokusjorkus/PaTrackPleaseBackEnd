package com.example.PaTrackPleaseBackend.Alarm.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AlarmResponseDto {

    private Long id;
    private String alarmName;
    private LocalDate alarmStart;
    private LocalDate alarmFinish;

    public AlarmResponseDto(Long id, String alarmName, LocalDate alarmStart, LocalDate alarmFinish) {
        this.id = id;
        this.alarmName = alarmName;
        this.alarmStart = alarmStart;
        this.alarmFinish = alarmFinish;
    }

    public Long getId() {
        return id;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public LocalDate getAlarmStart() {
        return alarmStart;
    }

    public LocalDate getAlarmFinish() {
        return alarmFinish;
    }

}
