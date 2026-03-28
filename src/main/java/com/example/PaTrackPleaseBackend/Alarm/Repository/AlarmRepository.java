package com.example.PaTrackPleaseBackend.Alarm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PaTrackPleaseBackend.Alarm.Model.Alarms;
import com.example.PaTrackPleaseBackend.Tasks.Model.Tasks;

public interface AlarmRepository extends JpaRepository<Alarms, Long> {

    // Returns Alarms belonging to a specific User ID
    List<Alarms> findByUserId(Long userId);

    // Returns Alarms belonging to a specific User Email
    // Note: This assumes your Alarms entity has a field named 'user' 
    // which contains an 'email' field.
    List<Alarms> findByUserEmail(String email);
}
