package com.example.PaTrackPleaseBackend.Alarm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PaTrackPleaseBackend.Alarm.Model.Alarms;

public interface AlarmRepository extends JpaRepository<Alarms, Long> {

    List<Alarms> findByUserId(Long userId);

    List<Alarms> findByUserEmail(String email);

    List<Alarms> findByTaskId(Long taskId);
}