package com.growing.backend.service;

import com.growing.backend.dto.PlantDTO;
import com.growing.backend.dto.PlantInfoDTO;
import com.growing.backend.entity.Plant;
import com.growing.backend.entity.PlantInfo;
import com.growing.backend.repository.PlantInfoRepository;
import com.growing.backend.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;
    private final PlantInfoRepository plantInfoRepository;

    // 식물 데이터 전달 (Id, 이름, 사진, 성장 일자)
    public List<Map<String, Object>> getPlantAllSearch() {
        List<Plant> plants = plantRepository.findAll();

        return plants.stream().map(plant -> {
            // Map 선언 후 식물 정보 찾기
            Map<String, Object> responseData = new HashMap<>();
            PlantInfo plantInfo = plantInfoRepository.findById(plant.getPlantId()).orElseThrow();

            // 식물 기본 정보 (Id, 이름)
            PlantDTO plantDTO = new PlantDTO();
            plantDTO.setPlantId(plant.getPlantId());
            plantDTO.setPlantName(plant.getPlantName());
            responseData.put("plant", plantDTO);

            // 식물 상세 정보 (사진, 성장 일자)
            PlantInfoDTO plantInfoDTO = new PlantInfoDTO();
            plantInfoDTO.setImage(plantInfo.getImage());
            plantInfoDTO.setDate(plantDate(plantInfo.getDate()));
            responseData.put("plantInfo", plantInfoDTO);

            return responseData;
        }).collect(Collectors.toList());
    }

    // 식물 성장 일자 계산
    public int plantDate(LocalDate startDate) {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        LocalDateTime dateTime1 = startDate.atStartOfDay();
        LocalDateTime dateTime2 = now.atStartOfDay();
        return (int) Duration.between(dateTime1, dateTime2).toDays();
    }

    // 식물 이름 변경
    public void updatePlant(PlantDTO dto) {
        Plant plant = plantRepository.findById(dto.getPlantId()).orElseThrow();
        plant.setPlantName(dto.getPlantName());
        plantRepository.save(plant);
    }
}