package com.example.PaTrackPleaseBackend.Alarm.Model;

import java.time.LocalDate;

import com.example.PaTrackPleaseBackend.Tasks.Model.Tasks;
import com.example.PaTrackPleaseBackend.User.Model.User;

import jakarta.persistence.*;

@Entity
@Table(name = "alarms")
public class Alarms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true)
    private String alarmName;

    @Column(nullable = false)
    private LocalDate alarmStart;

    @Column(nullable = true)
    private LocalDate alarmFinish;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = true)
    private Tasks task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Links this alarm to a specific user
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Tasks getTask() {
        return task;
    }

    public void setTask(Tasks task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
