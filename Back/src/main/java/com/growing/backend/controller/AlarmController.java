package com.growing.backend.controller;

import com.growing.backend.dto.response.AlarmResponse;
import com.growing.backend.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
