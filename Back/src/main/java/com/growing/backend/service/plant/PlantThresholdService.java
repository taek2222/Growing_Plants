package com.growing.backend.service.plant;
import com.growing.backend.dto.request.PlantInfoDTO;
import com.growing.backend.dto.response.PlantDTO;
import com.growing.backend.entity.Plant;
import com.growing.backend.entity.PlantInfo;
import com.growing.backend.entity.PlantThreshold;
import com.growing.backend.entity.PlantWater;
import com.growing.backend.repository.PlantInfoRepository;
import com.growing.backend.repository.PlantThresholdRepository;
import com.growing.backend.repository.PlantWaterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantThresholdService {
    private final PlantThresholdRepository plantThresholdRepository;
    private final PlantWaterRepository plantWaterRepository;
    private final PlantInfoRepository plantInfoRepository;

    // 식물 데이터 (기준치) 반환
    public void getPlantThreshold(PlantDTO plantDTO, Plant plant) {
        PlantThreshold plantThreshold = plantThresholdRepository.findById(plant.getPlantId())
                .orElseThrow(() -> new RuntimeException("[PlantThresholdService] PlantThreshold Not Found : " + plant.getPlantId()));
        plantDTO.setLightThreshold(plantThreshold.getLightThreshold());
        plantDTO.setSoilThreshold(plantThreshold.getSoilThreshold());
        plantDTO.setWaterThreshold(plantThreshold.getWaterThreshold());
    }

    // 식물 기준치 변경
    public void updatePlantThreshold(PlantInfoDTO dto) {
        PlantThreshold plantThreshold = plantThresholdRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("[PlantThresholdService] PlantThreshold Not Found : " + dto.getId()));
        plantThreshold.setLightThreshold(dto.getLightThreshold());
        plantThreshold.setSoilThreshold(dto.getSoilThreshold());
        plantThreshold.setWaterThreshold(dto.getWaterThreshold());
        plantThresholdRepository.save(plantThreshold);
    }

    // 습도 센서 값 체크
    public void checkSoil(List<String> response, double[] soilMoisture) {
        for(int i = 0; i < soilMoisture.length; i++) {
            // 식물 기준치 ID 조회
            PlantThreshold plantThreshold = plantThresholdRepository.findById(i+1)
                    .orElseThrow(() -> new EntityNotFoundException("[PlantStateSoilService] PlantThreshold Not Found"));

            // 식물 물 ID 조회
            PlantWater plantWater = plantWaterRepository.findById(i+1)
                    .orElseThrow(() -> new EntityNotFoundException("[PlantStateSoilService] PlantWater Not Found"));

            // 식물 습도 기준치 변수화, 물 Flag
            double soilThreshold = plantThreshold.getSoilThreshold();
            boolean waterFlag = plantWater.isWaterToggle();

            // 습도 기준치 이하일 경우
            if(soilThreshold >= soilMoisture[i] || waterFlag) {
                plantWater.setWaterToggle(false);
                plantWaterRepository.save(plantWater);
                // 물 주기 상태 코드 전달
                response.add("2-" + (i+1));
            }
        }
    }

    // 조도 센서 값 체크
    public void checkLight(List<String> response, double light, boolean[] lightStatus) {
        int countTime;

        for (int i = 0; i < lightStatus.length; i++) {
            // 식물 ID 조회
            PlantInfo plantInfo = plantInfoRepository.findById(i+1)
                    .orElseThrow(() -> new RuntimeException("Error Light Method : "));

            // 식물 기준치 ID 조회
            PlantThreshold plantThreshold = plantThresholdRepository.findById(i+1)
                    .orElseThrow(() -> new EntityNotFoundException("[PlantStateSoilService] PlantThreshold Not Found"));

            // 식물 조도 기준치, 식물등 시간, 햇빛 시간 변수화
            double lightThreshold = plantThreshold.getLightThreshold();
            int sunlightDuration = plantInfo.getSunlightDuration();
            int growLightDuration = plantInfo.getGrowLightDuration();

            // 식물 시간 최대 카운트
            if(plantThreshold.getSunLightMax() <= sunlightDuration + growLightDuration)
                countTime = 0;
            else countTime = 1;

            // 조도 센서 값이 기준치 이하일 경우
            if (light <= lightThreshold) {
                // 센서 기준치 이하 & 센서가 이미 켜져 있는 경우
                if (lightStatus[i])
                    growLightDuration += countTime; // 식물등 시간 카운트 (분)
                else {
                    response.add("3-" + (i+1)); // 식물등 ON
                    sunlightDuration += countTime; // 햇빛 시간 카운트 (분)
                }
            } else {
                if (lightStatus[i]) {
                    response.add("4-" + (i+1)); // 식물등 OFF
                    growLightDuration += countTime; // 식물등 시간 카운트 (분)
                } else
                    sunlightDuration += countTime; // 햇빛 시간 카운트 (분)
            }

            // 식물 전등 상태 업데이트
            if (plantInfo.isLightStatus() != lightStatus[i])
                plantInfo.setLightStatus(lightStatus[i]);

            // 식물 데이터 설정
            plantInfo.setSunlightDuration(sunlightDuration);
            plantInfo.setGrowLightDuration(growLightDuration);

            // 식물 데이터 저장
            plantInfoRepository.save(plantInfo);
        }
    }

    // 물통 수위 센서 값 체크
    void checkPlantStateWaterAmount(List<String> response, int waterAmount) {
        PlantThreshold plantThreshold = plantThresholdRepository.findById(1).orElseThrow(() -> new EntityNotFoundException("[PlantStateSoilService] PlantThreshold Not Found"));

        if(waterAmount < plantThreshold.getWaterThreshold()) {
            // 알림 전달 추가 해야함.
        }

    }
}
