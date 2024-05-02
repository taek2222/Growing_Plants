package com.growing.backend.service.plant;

import com.growing.backend.dto.response.PlantDTO;
import com.growing.backend.dto.request.PlantInfoDTO;
import com.growing.backend.entity.Plant;
import com.growing.backend.entity.PlantInfo;
import com.growing.backend.repository.PlantInfoRepository;
import com.growing.backend.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PlantInfoService plantInfoService;

    // 식물 데이터 전달 (ID, 이름, 사진, 성장 일자)
    public List<PlantDTO> getPlant() {
        List<Plant> plants = plantRepository.findAll();

        return plants.stream().map(plant -> {
            // Map 선언 후 식물 정보 찾기
            PlantInfo plantInfo = plantInfoRepository.findById(plant.getPlantId()).orElseThrow();

            // 식물 기본 정보 (ID, 이름, 사진, 성장 일자)
            PlantDTO plantDTO = new PlantDTO();
            plantDTO.setPlantId(plant.getPlantId());
            plantDTO.setPlantName(plant.getPlantName());
            plantDTO.setImage(plantInfo.getImage());
            plantDTO.setDate(getPlantDate(plantInfo.getDate()));

            return plantDTO;
        }).collect(Collectors.toList());
    }

    // 식물 성장 일자 계산
    public int getPlantDate(LocalDate startDate) {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        // 성장 일자, Today 일자 변수
        LocalDateTime dateTime1 = startDate.atStartOfDay();
        LocalDateTime dateTime2 = now.atStartOfDay();

        // 생성, Today 일자를 기반으로 사이 날짜 반환
        return (int) Duration.between(dateTime1, dateTime2).toDays();
    }

    // 식물 정보 변경 (이름, 성장 시작 일자, 습도 기준치, 조도 기준치)
    @Transactional
    public void updatePlant(PlantInfoDTO dto) {
        Plant plant = plantRepository.findById(dto.getId()).orElseThrow();
        plant.setPlantName(dto.getName());
        plantRepository.save(plant);

        plantInfoService.updatePlantInfo(dto); // 성장 시작 일자, (습도, 조도) 기준치
    }
}
