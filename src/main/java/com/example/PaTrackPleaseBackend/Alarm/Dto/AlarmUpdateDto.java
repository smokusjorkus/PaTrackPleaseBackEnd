package com.example.PaTrackPleaseBackend.Alarm.Dto;

import java.time.LocalDate;

public class AlarmUpdateDto {

    private Long id;
    private String alarmName;
    private LocalDate alarmStart;
    private LocalDate alarmFinish;

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
