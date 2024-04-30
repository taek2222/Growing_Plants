package com.growing.backend.service;

import com.growing.backend.dto.PlantRequestDTO;
import com.growing.backend.entity.PlantState;
import com.growing.backend.repository.PlantStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PlantStateService {
    private final PlantStateRepository plantStateRepository;

    // 조도, 토양 습도 기준치
    static double stLIT = 40.0;
    static double stSMT = 25.0;

//    public int plantStateDataTest(PlantRequestDTO DTO) {
//
//    }

    public PlantState plantStateSave(PlantRequestDTO DTO) {

        PlantState plantState = new PlantState();

        plantState.setDate(LocalDate.now(ZoneId.of("Asia/Seoul")));
        plantState.setTime(LocalTime.now(ZoneId.of("Asia/Seoul")).truncatedTo(ChronoUnit.MINUTES));
        plantState.setLightIntensity(DTO.getLightIntensity());
        plantState.setAirTemperature(DTO.getAirTemperature());
        plantState.setAirHumidity(DTO.getAirHumidity());
        plantState.setSoilMoisture1(DTO.getSoilMoisture1());
        plantState.setSoilMoisture2(DTO.getSoilMoisture2());

        plantStateRepository.save(plantState);

        return plantState;
    }
}
