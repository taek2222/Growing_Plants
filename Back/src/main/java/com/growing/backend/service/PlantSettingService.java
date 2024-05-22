package com.growing.backend.service;

import com.growing.backend.dto.response.PlantSettingResponse;
import com.growing.backend.service.plant.PlantInfoService;
import com.growing.backend.service.plant.PlantService;
import com.growing.backend.service.plant.PlantThresholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlantSettingService {
    private final PlantService plantService;
    private final PlantInfoService plantInfoService;
    private final PlantThresholdService plantThresholdService;

    // 식물 설정 정보 요청
    public PlantSettingResponse getPlantSetting(int plantId) {
        PlantSettingResponse response = new PlantSettingResponse();

        response.setPlantSetting(plantService.getPlantSetting(plantId));
        response.setPlantInfoSetting(plantInfoService.getPlantInfoSetting(plantId));
        response.setPlantThresholdSetting(plantThresholdService.getPlantThresholdSetting(plantId));

        return response;
    }
}