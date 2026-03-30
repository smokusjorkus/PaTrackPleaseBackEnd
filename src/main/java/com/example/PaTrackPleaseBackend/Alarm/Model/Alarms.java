package com.example.PaTrackPleaseBackend.Alarm.Model;

import java.time.LocalDateTime;

import com.example.PaTrackPleaseBackend.Tasks.Model.Tasks;
import com.example.PaTrackPleaseBackend.User.Model.User;

import jakarta.persistence.*;

@Entity
@Table(name = "alarms")
public class Alarms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String alarmName;

    @Column(nullable = true)
    private LocalDateTime alarmStart;

    @Column(nullable = true)
    private LocalDateTime alarmFinish;

    @Column(nullable = true)
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = true)
    private Tasks task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Alarms() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}