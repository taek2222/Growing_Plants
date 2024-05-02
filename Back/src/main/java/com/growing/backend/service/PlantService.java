package com.growing.backend.service;

import com.growing.backend.dto.response.PlantDTO;
import com.growing.backend.dto.request.PlantInfoDTO;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;
    private final PlantInfoRepository plantInfoRepository;

    // 식물 데이터 전달 (ID, 이름, 사진, 성장 일자)
    public List<PlantDTO> getPlantAllSearch() {
        List<Plant> plants = plantRepository.findAll();

        return plants.stream().map(plant -> {
            // Map 선언 후 식물 정보 찾기
            PlantInfo plantInfo = plantInfoRepository.findById(plant.getPlantId()).orElseThrow();

            // 식물 기본 정보 (ID, 이름, 사진, 성장 일자)
            PlantDTO plantDTO = new PlantDTO();
            plantDTO.setPlantId(plant.getPlantId());
            plantDTO.setPlantName(plant.getPlantName());
            plantDTO.setImage(plantInfo.getImage());
            plantDTO.setDate(plantDate(plantInfo.getDate()));

            return plantDTO;
        }).collect(Collectors.toList());
    }

    // 식물 성장 일자 계산
    public int plantDate(LocalDate startDate) {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        // 성장 일자, Today 일자 변수
        LocalDateTime dateTime1 = startDate.atStartOfDay();
        LocalDateTime dateTime2 = now.atStartOfDay();

        // 생성, Today 일자를 기반으로 사이 날짜 반환
        return (int) Duration.between(dateTime1, dateTime2).toDays();
    }

    // 식물 이름 변경
    public void updatePlant(PlantInfoDTO dto) {
        Plant plant = plantRepository.findById(dto.getId()).orElseThrow();
        plant.setPlantName(dto.getName());
        plantRepository.save(plant);

        PlantInfo plantInfo = plantInfoRepository.findById(dto.getId()).orElseThrow();
        plantInfo.setDate(dto.getDate());
        plantInfoRepository.save(plantInfo);
    }
}
