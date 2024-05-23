package com.growing.backend.service;

import com.growing.backend.dto.response.AlarmResponse;
import com.growing.backend.entity.Alarm;
import com.growing.backend.repository.AlarmRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    // 알람 목록 반환
    @Transactional
    public List<AlarmResponse> getAlarm() {
        List<Alarm> alarms = alarmRepository.findAll();

        return alarms.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 알람 삭제
    @Transactional
    public void deleteAlarm(Long alarmId) {
        alarmRepository.deleteById(alarmId);
    }

    // 알람 정보 DTO 변환
    private AlarmResponse convertToDto(Alarm alarm) {
        AlarmResponse dto = new AlarmResponse();
        dto.setId(alarm.getId());
        dto.setTitle(alarm.getTitle());
        dto.setContents(alarm.getContents());
        dto.setReadFlag(alarm.isReadFlag());

        // 읽음 처리
        setAlarmReadFlag(dto.getId());
        return dto;
    }

    // 알람 읽음 처리
    public void setAlarmReadFlag(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + "Alarm Not Found Id : " + alarmId));

        alarm.setReadFlag(true);
        alarmRepository.save(alarm);
    }
}
