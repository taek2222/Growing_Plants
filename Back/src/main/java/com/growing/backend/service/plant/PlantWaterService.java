package com.growing.backend.service.plant;

import com.growing.backend.entity.PlantWater;
import com.growing.backend.repository.PlantWaterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlantWaterService {
    private final PlantWaterRepository plantWaterRepository;

    @Transactional
    public String toggleWater(int plantId) {
        PlantWater plantWater = plantWaterRepository.findById(plantId).orElseThrow(() -> new EntityNotFoundException("[PlantWaterService] Not Found Id : " + plantId));

        if(plantWater.isWaterToggle())
            return "이미 물주기 액션이 실행되고 있습니다. 잠시만 기다려주세요.";
        else {
            plantWater.setWaterToggle(true);
            plantWaterRepository.save(plantWater);
            return "물주기 액션이 실행되었습니다. 잠시만 기다려주세요.";
        }
    }
}