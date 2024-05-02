package com.growing.backend.service.plant.state;

import com.growing.backend.entity.PlantInfo;
import com.growing.backend.repository.PlantInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantStateSoilService {
    private final PlantInfoRepository plantInfoRepository;

    // 습도 센서 값 체크
    public void checkSoil(List<String> response, double[] soilMoisture) {
        for(int i = 1; i < 3; i++) {
            // 식물 ID 조회
            PlantInfo plantInfo = plantInfoRepository.findById(i).orElseThrow(() -> new RuntimeException("Error Soil Method : "));

            // 식물 습도 기준치 변수화
            double soilThreshold = plantInfo.getSoilThreshold();

            // 습도 기준치 이하일 경우
            if(soilThreshold >= soilMoisture[i-1])
                // 물 주기 상태 코드 전달
                response.add("2-"+i);
        }
    }
}
