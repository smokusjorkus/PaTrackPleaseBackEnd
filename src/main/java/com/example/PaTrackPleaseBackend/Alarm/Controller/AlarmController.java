package com.example.PaTrackPleaseBackend.Alarm.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.PaTrackPleaseBackend.Alarm.Dto.AlarmCreateDto;
import com.example.PaTrackPleaseBackend.Alarm.Dto.AlarmResponseDto;
import com.example.PaTrackPleaseBackend.Alarm.Model.Alarms;
import com.example.PaTrackPleaseBackend.Alarm.Service.AlarmService;
import com.example.PaTrackPleaseBackend.Tasks.Model.Tasks;
import com.example.PaTrackPleaseBackend.Tasks.Repository.TaskRepository;
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
    private TaskRepository taskRepository;

    @PostMapping
    public ResponseEntity<?> createAlarm(
            @RequestParam("email") String email,
            @RequestBody AlarmCreateDto dto) {
        try {
            User user = userService.getUserByEmail(email);

            if (user == null) {
                return ResponseEntity.badRequest().body("User not found");
            }

            Alarms alarm = new Alarms();
            alarm.setAlarmName(dto.getAlarmName());
            alarm.setAlarmStart(dto.getAlarmStart());
            alarm.setAlarmFinish(dto.getAlarmFinish());
            alarm.setActive(true);
            alarm.setUser(user);

            if (dto.getTaskId() != null) {
                Tasks task = taskRepository.findById(dto.getTaskId()).orElse(null);
                if (task != null) {
                    alarm.setTask(task);
                }
            }

            Alarms created = alarmService.createAlarm(alarm);

            AlarmResponseDto response = new AlarmResponseDto(
                    created.getId(),
                    created.getAlarmName(),
                    created.getAlarmStart(),
                    created.getAlarmFinish(),
                    created.isActive());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating alarm: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AlarmResponseDto>> getAlarms(
            @RequestParam(value = "email", required = false) String email) {

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
                        alarm.getAlarmFinish(),
                        alarm.isActive()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(alarms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlarmById(@PathVariable Long id) {
        Alarms alarm = alarmService.getAlarmById(id);

        if (alarm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Alarm not found with id " + id);
        }

        AlarmResponseDto response = new AlarmResponseDto(
                alarm.getId(),
                alarm.getAlarmName(),
                alarm.getAlarmStart(),
                alarm.getAlarmFinish(),
                alarm.isActive());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlarm(@PathVariable Long id) {
        try {
            alarmService.deleteAlarm(id);
            return ResponseEntity.ok("Alarm deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting alarm");
        }
    }
}