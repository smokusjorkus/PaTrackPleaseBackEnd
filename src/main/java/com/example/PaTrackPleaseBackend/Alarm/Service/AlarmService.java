package com.example.PaTrackPleaseBackend.Alarm.Service;

import com.example.PaTrackPleaseBackend.Tasks.Repository.TaskRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PaTrackPleaseBackend.Alarm.Model.Alarms;
import com.example.PaTrackPleaseBackend.Alarm.Repository.AlarmRepository;

import jakarta.transaction.Transactional;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    @Transactional
    public Alarms createAlarm(Alarms alarm) {
        return alarmRepository.save(alarm);
    }

    public Alarms getAlarmById(Long id) {
        return alarmRepository.findById(id).orElseThrow(() -> new RuntimeException("Alarm not found with id: " + id));
    }

    public List<Alarms> getAllAlarms() {
        return alarmRepository.findAll();
    }

    public List<Alarms> getAlarmsByEmail(String email) {
        return alarmRepository.findByUserEmail(email);
    }

    @Transactional // Added this for safe deletion
    public void deleteAlarm(Long id) {
        if (!alarmRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Alarm not found with id: " + id);
        }
        alarmRepository.deleteById(id);
    }
}
