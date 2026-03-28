package com.example.PaTrackPleaseBackend.Alarm.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.PaTrackPleaseBackend.Alarm.Dto.AlarmCreateDto;
import com.example.PaTrackPleaseBackend.Alarm.Dto.AlarmResponseDto;
import com.example.PaTrackPleaseBackend.Alarm.Model.Alarms;
import com.example.PaTrackPleaseBackend.Alarm.Repository.AlarmRepository;
import com.example.PaTrackPleaseBackend.Alarm.Service.AlarmService;

import com.example.PaTrackPleaseBackend.User.Model.User;
import com.example.PaTrackPleaseBackend.User.Service.UserService;

@RestController
@RequestMapping("/api/alarms")
@CrossOrigin(origins = "http://localhost:5173")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private UserService userService;

    @Autowired
    private AlarmRepository alarmRepository;

    @PostMapping
    public ResponseEntity<?> createAlarm(@RequestParam("email") String email, @RequestBody AlarmCreateDto dto) {
        try {
            User user = userService.getUserByEmail(email);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found");
            }

            Alarms alarm = new Alarms();
            alarm.setAlarmName(dto.getAlarmName());
            alarm.setAlarmStart(dto.getAlarmStart());
            alarm.setAlarmFinish(dto.getAlarmFinish());

            // IMPORTANT: Link the alarm to the user we found
            // Make sure your Alarms model has a setUser(User user) method!
            alarm.setUser(user);

            Alarms created = alarmService.createAlarm(alarm);

            // Return a DTO instead of the full Entity to avoid infinite recursion/leaking passwords
            return new ResponseEntity<>(new AlarmResponseDto(
                    created.getId(),
                    created.getAlarmName(),
                    created.getAlarmStart(),
                    created.getAlarmFinish()
            ), HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AlarmResponseDto>> getAlarms(@RequestParam(value = "email", required = false) String email) {

        List<Alarms> alarmList;
        if (email != null && !email.isEmpty()) {
            alarmList = alarmService.getAlarmsByEmail(email);
        } else {
            alarmList = alarmService.getAllAlarms();
        }

        List<AlarmResponseDto> alarms = alarmList.stream()
                .map(alarm -> new AlarmResponseDto(
                alarm.getId(),
                alarm.getAlarmName(),
                alarm.getAlarmStart(),
                alarm.getAlarmFinish()
        ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(alarms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlarmById(@PathVariable Long id) {
        Alarms alarm = alarmService.getAlarmById(id);

        if (alarm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found with id " + id);
        }

        return ResponseEntity
                .ok(new AlarmResponseDto(alarm.getId(), alarm.getAlarmName(), alarm.getAlarmStart(), alarm.getAlarmFinish()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlarm(@PathVariable Long id) {
        try {
            alarmService.deleteAlarm(id);
            return ResponseEntity.ok("Task deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting task");
        }
    }

}
