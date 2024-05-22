package com.growing.backend.service.plant;

import com.growing.backend.dto.response.PlantDTO;
import com.growing.backend.dto.response.PlantSettingResponse;
import com.growing.backend.entity.Plant;
import com.growing.backend.entity.PlantInfo;
import com.growing.backend.repository.PlantInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantInfoService {
    private final PlantInfoRepository plantInfoRepository;

    // 식물 데이터 (사진, 성장 일자, 조도, 습도, 햇빛, 식물등) 반환
    public void getPlantInfo(PlantDTO plantDTO, Plant plant) {
        PlantInfo plantInfo = plantInfoRepository.findById(plant.getPlantId()).orElseThrow(() -> new RuntimeException("Not Found PlantInfo : " + plant.getPlantId()));

        plantDTO.setImage(plantInfo.getImage());
        plantDTO.setDate(getPlantDate(plantInfo.getDate()));
        plantDTO.setLightStatus(plantInfo.isLightStatus());
        plantDTO.setSunlightDuration(plantInfo.getSunlightDuration());
        plantDTO.setGrowLightDuration(plantInfo.getGrowLightDuration());
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

    // 식물 습득 정보 데이터 초기화 [(햇빛, 식물등 시간)]
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetPlantInfo() {
        List<PlantInfo> plantInfoList = plantInfoRepository.findAll();

        for(PlantInfo plantInfo : plantInfoList) {
            plantInfo.setSunlightDuration(0);
            plantInfo.setGrowLightDuration(0);
        }

        plantInfoRepository.saveAll(plantInfoList);
    }

    // 식물 설정 정보 요청
    public PlantSettingResponse.PlantInfoSetting getPlantInfoSetting(int plantId) {
        PlantInfo plantInfo = plantInfoRepository.findById(plantId).orElseThrow(() -> new EntityNotFoundException("[getSettingPlant] PlantInfo Not Found Id : " + plantId));

       return new PlantSettingResponse.PlantInfoSetting(getPlantDate(plantInfo.getDate()));
    }

    // 식물 정보 변경
    public void updatePlantInfoSetting(int plantId, LocalDate date) {
        PlantInfo plantInfo = plantInfoRepository.findById(plantId).orElseThrow(() -> new EntityNotFoundException( this.getClass().getSimpleName() + "PlantInfo Not Found Id : " + plantId));

        plantInfo.setDate(date);
        plantInfoRepository.save(plantInfo);
    }
}
