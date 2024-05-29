package com.growing.backend.controller;

import com.growing.backend.dto.response.AlarmResponse;
import com.growing.backend.service.plant.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;

    // 알람 목록 반환
    @GetMapping
    public List<AlarmResponse> getAlarm() {
        return alarmService.getAlarm();
    }

    // 알람 삭제
    @DeleteMapping("/{alarm_id}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable("alarm_id") Long alarmId) {
        try {
            alarmService.deleteAlarm(alarmId);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
