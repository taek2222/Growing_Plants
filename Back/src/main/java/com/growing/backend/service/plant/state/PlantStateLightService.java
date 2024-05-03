package com.growing.backend.service.plant.state;

import com.growing.backend.entity.PlantInfo;
import com.growing.backend.repository.PlantInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantStateLightService {
    private final PlantInfoRepository plantInfoRepository;

    // 조도 센서 값 체크
    public void checkLight(List<String> response, double light, boolean[] lightStatus) {
        for(int i = 1; i < 3; i++) {
            // 식물 ID 조회
            PlantInfo plantInfo = plantInfoRepository.findById(i).orElseThrow(() -> new RuntimeException("Error Light Method : "));

            // 식물 조도 기준치, 식물등 시간, 햇빛 시간 변수화
            double lightThreshold = plantInfo.getLightThreshold();
            int sunlightDuration = plantInfo.getSunlightDuration();
            int growLightDuration = plantInfo.getGrowLightDuration();

            // 조도 센서 값이 기준치 이하일 경우
            if(light <= lightThreshold) {
                // 센서 기준치 이하 & 센서가 이미 켜져 있는 경우
                if (lightStatus[i-1])
                    growLightDuration += 1; // 식물등 시간 카운트 (분)
                else {
                    response.add("3-" + i); // 식물등 ON
                    sunlightDuration += 1; // 햇빛 시간 카운트 (분)
                }
            } else {
                if (lightStatus[i-1]) {
                    response.add("4-" + i); // 식물등 OFF
                    growLightDuration += 1; // 식물등 시간 카운트 (분)
                }
                else
                    sunlightDuration += 1; // 햇빛 시간 카운트 (분)
            }

            // 식물 데이터 설정
            plantInfo.setSunlightDuration(sunlightDuration);
            plantInfo.setGrowLightDuration(growLightDuration);

            // 식물 데이터 저장
            plantInfoRepository.save(plantInfo);
        }
    }


}
