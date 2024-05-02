package com.growing.backend.service.plant;

import com.growing.backend.dto.request.PlantInfoDTO;
import com.growing.backend.entity.PlantInfo;
import com.growing.backend.repository.PlantInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlantInfoService {
    private final PlantInfoRepository plantInfoRepository;

    // 식물 정보 변경 (성장 시작 일자, (습도, 조도) 기준치)
    public void updatePlantInfo(PlantInfoDTO dto) {
        PlantInfo plantInfo = plantInfoRepository.findById(dto.getId()).orElseThrow();
        plantInfo.setDate(dto.getDate());
        plantInfoRepository.save(plantInfo);
    }
}
