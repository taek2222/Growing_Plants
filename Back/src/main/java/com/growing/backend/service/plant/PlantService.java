package com.growing.backend.service.plant;

import com.growing.backend.dto.response.PlantDTO;
import com.growing.backend.dto.response.PlantSettingResponse;
import com.growing.backend.entity.Plant;
import com.growing.backend.repository.PlantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;
    private final PlantInfoService plantInfoService;
    private final PlantThresholdService plantThresholdService;
    private final AlarmService alarmService;

    // 식물 데이터 (ID, 이름, 사진, 성장 일자, 조도, 습도, 햇빛, 식물등, 전등 상태) 반환
    public List<PlantDTO> getPlant() {
        List<Plant> plants = plantRepository.findAll();

        return plants.stream().map(plant -> {

            PlantDTO plantDTO = new PlantDTO();
            plantDTO.setPlantId(plant.getPlantId());
            plantDTO.setPlantName(plant.getPlantName());
            plantInfoService.getPlantInfo(plantDTO, plant);
            plantThresholdService.getPlantThreshold(plantDTO, plant);
            plantDTO.setNewAlarm(alarmService.isNewAlarm());

            return plantDTO;
        }).collect(Collectors.toList());
    }

    // 식물 설정 정보 요청
    public PlantSettingResponse.PlantSetting getPlantSetting(int plantId) {
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + " Plant Not Found Id : " + plantId));
        return new PlantSettingResponse.PlantSetting(plant.getPlantId(), plant.getPlantName());
    }

    // 식물 정보 변경
    public void updatePlantSetting(int plantId, String plantName) {
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + " Plant Not Found Id : " + plantId));

        plant.setPlantName(plantName);
        plantRepository.save(plant);
    }
}
