package com.growing.backend.service;

import com.growing.backend.dto.request.PlantSettingRequest;
import com.growing.backend.dto.response.PlantSettingResponse;
import com.growing.backend.service.plant.PlantInfoService;
import com.growing.backend.service.plant.PlantService;
import com.growing.backend.service.plant.PlantThresholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 식물 설정 변경
    @Transactional
    public void updatePlantSetting(int plantId, PlantSettingRequest dto) {
        plantService.updatePlantSetting(plantId, dto.getPlantName()); // 식물 아이디, 식물 이름
        plantInfoService.updatePlantInfoSetting(plantId, dto.getDate()); // 성장 일자
        plantThresholdService.updatePlantThresholdSetting(plantId, dto.getLightThreshold(), dto.getSoilThreshold(), dto.getWaterThreshold(), dto.getSunLightMax()); // 습도, 조도, 물 기준치, 햇빛 + 식물등 시간 최대치
    }
}