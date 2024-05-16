package com.growing.backend.service.plant.state;

import com.growing.backend.entity.PlantInfo;
import com.growing.backend.entity.PlantWater;
import com.growing.backend.repository.PlantInfoRepository;
import com.growing.backend.repository.PlantWaterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantStateSoilService {
    private final PlantInfoRepository plantInfoRepository;
    private final PlantWaterRepository plantWaterRepository;

    // 습도 센서 값 체크
    public void checkSoil(List<String> response, double[] soilMoisture) {
        for(int i = 0; i < soilMoisture.length; i++) {
            // 식물 정보 ID 조회
            PlantInfo plantInfo = plantInfoRepository.findById(i+1)
                    .orElseThrow(() -> new EntityNotFoundException("[PlantStateSoilService] PlantInfo Not Found"));

            // 식물 물 ID 조회
            PlantWater plantWater = plantWaterRepository.findById(i+1)
                    .orElseThrow(() -> new EntityNotFoundException("[PlantStateSoilService] PlantWater Not Found"));

            // 식물 습도 기준치 변수화, 물 Flag
            double soilThreshold = plantInfo.getSoilThreshold();
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
}
