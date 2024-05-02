package com.growing.backend.service;

import com.growing.backend.dto.request.PlantStateDTO;
import com.growing.backend.entity.PlantState;
import com.growing.backend.repository.PlantStateRepository;
import com.growing.backend.service.plant.state.LightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlantStateService {
    private final PlantStateRepository plantStateRepository;
    private final LightService lightService;

    // 토양 습도 기준치
    static double stSMT = 25.0;

    // 수집 데이터 처리 메소드
//    public int plantStateDataTest(PlantStateDTO DTO) {
//        Map<String, Object> response = new HashMap<>();
//
//        // 상태 코드 배열로 저장
//        boolean[] lightStatus = new boolean[] {DTO.isLightStatus2(), DTO.isLightStatus2()};
//
//        // 조도 센서 값 상태 변환 및 시간 카운팅
//        lightService.lightIntensityCheck(response, DTO.getLightIntensity(), lightStatus);
//    }


    // 데이터 저장 메소드
    public PlantState plantStateSave(PlantStateDTO DTO) {

        PlantState plantState = new PlantState();

        // 전달 날짜 및 시간 기준 저장
        plantState.setDate(LocalDate.now(ZoneId.of("Asia/Seoul")));
        plantState.setTime(LocalTime.now(ZoneId.of("Asia/Seoul")).truncatedTo(ChronoUnit.SECONDS));
        plantState.setLightIntensity(DTO.getLightIntensity());
        plantState.setAirTemperature(DTO.getAirTemperature());
        plantState.setAirHumidity(DTO.getAirHumidity());
        plantState.setSoilMoisture1(DTO.getSoilMoisture1());
        plantState.setSoilMoisture2(DTO.getSoilMoisture2());

        plantStateRepository.save(plantState);

        return plantState;
    }


}
