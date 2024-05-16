package com.growing.backend.service.plant;

import com.growing.backend.dto.request.PlantInfoDTO;
import com.growing.backend.dto.response.PlantDTO;
import com.growing.backend.entity.Plant;
import com.growing.backend.entity.PlantInfo;
import com.growing.backend.repository.PlantInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class PlantInfoService {
    private final PlantInfoRepository plantInfoRepository;

    // 식물 데이터 (사진, 성장 일자, 조도, 습도, 햇빛, 식물등) 반환
    public void getPlantInfo(PlantDTO plantDTO, Plant plant) {
        PlantInfo plantInfo = plantInfoRepository.findById(plant.getPlantId()).orElseThrow(() -> new RuntimeException("Not Found PlantInfo : " + plant.getPlantId()));

        plantDTO.setImage(plantInfo.getImage());
        plantDTO.setDate(getPlantDate(plantInfo.getDate()));
        plantDTO.setLightThreshold(plantInfo.getLightThreshold());
        plantDTO.setLightStatus(plantInfo.isLightStatus());
        plantDTO.setSoilThreshold(plantInfo.getSoilThreshold());
        plantDTO.setSunlightDuration(plantInfo.getSunlightDuration());
        plantDTO.setGrowLightDuration(plantInfo.getGrowLightDuration());
        plantDTO.setWaterThreshold(plantInfo.getWaterThreshold());
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

    // 식물 정보 변경 (성장 일자, (습도, 조도) 기준치)
    public void updatePlantInfo(PlantInfoDTO dto) {
        PlantInfo plantInfo = plantInfoRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Update PlantInfo Not Found : " + dto.getId()));
        plantInfo.setDate(dto.getDate());
        plantInfo.setLightThreshold(dto.getLightThreshold());
        plantInfo.setSoilThreshold(dto.getSoilThreshold());
        plantInfo.setWaterThreshold(dto.getWaterThreshold());
        plantInfoRepository.save(plantInfo);
    }
}
