package com.example.PaTrackPleaseBackend.Alarm.Dto;

import java.time.LocalDate;

public class AlarmCreateDto {

    private String alarmName;
    private LocalDate alarmStart;
    private LocalDate alarmFinish;

    public AlarmCreateDto() {
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public LocalDate getAlarmStart() {
        return alarmStart;
    }

    public void setAlarmStart(LocalDate alarmStart) {
        this.alarmStart = alarmStart;
    }

    public LocalDate getAlarmFinish() {
        return alarmFinish;
    }

    public void setAlarmFinish(LocalDate alarmFinish) {
        this.alarmFinish = alarmFinish;
    }

}
