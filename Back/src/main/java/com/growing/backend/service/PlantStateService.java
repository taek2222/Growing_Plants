package com.growing.backend.service;

import com.growing.backend.dto.PlantRequestDTO;
import com.growing.backend.entity.PlantState;
import com.growing.backend.repository.PlantStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // 조도, 토양 습도 기준치
    static double stLIT = 30.0;
    static double stSMT = 25.0;

//    // 수집 데이터 처리 메소드
//    public int plantStateDataTest(PlantRequestDTO DTO) {
//        Map<String, Object> response = new HashMap<>();
//
//    }
//
//    // 조도 센서 값
//    public int lightIntensityCheck(Map<String, Object> response, double light) {
//        if(light > stLIT)
//
//    }

    // 데이터 저장 메소드
    public PlantState plantStateSave(PlantRequestDTO DTO) {

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

    public PlantRequestDTO getPlantState() {
        Pageable topOne = PageRequest.of(0, 1);
        PlantState latestPlantState = plantStateRepository.findAllByOrderByDateDescTimeDesc(topOne).get(0);

        PlantRequestDTO DTO = new PlantRequestDTO();
        DTO.setAirTemperature(latestPlantState.getAirTemperature());
        DTO.setAirHumidity(latestPlantState.getAirHumidity());

        return DTO;
    }
}
