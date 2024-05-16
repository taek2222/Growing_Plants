package com.growing.backend.service;

import com.growing.backend.entity.Plant;
import com.growing.backend.entity.PlantWaterSupply;
import com.growing.backend.repository.PlantRepository;
import com.growing.backend.repository.PlantWaterSupplyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class PlantWaterSupplyService {
    private final PlantWaterSupplyRepository plantWaterSupplyRepository;
    private final PlantRepository plantRepository;

    @Transactional
    public void addWaterSupplyList(int plantId) {
        PlantWaterSupply plantWaterSupply = new PlantWaterSupply();

        // plantId
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new EntityNotFoundException("[PlantWaterSupplyService] Plant Not Found Id : " + plantId));
        plantWaterSupply.setPlant(plant);

        // supplyDate
        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        plantWaterSupply.setSupplyDate(localDate);

        // supplyTime
        LocalTime localTime = LocalTime.now(ZoneId.of("Asia/Seoul")).withNano(0);
        plantWaterSupply.setSupplyTime(localTime);

        plantWaterSupplyRepository.save(plantWaterSupply);
    }
}
