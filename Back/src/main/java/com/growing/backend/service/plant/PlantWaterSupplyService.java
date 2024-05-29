package com.growing.backend.service.plant;

import com.growing.backend.dto.response.PlantWaterSupplyDTO;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantWaterSupplyService {
    private final PlantWaterSupplyRepository plantWaterSupplyRepository;
    private final AlarmService alarmService;
    private final PlantRepository plantRepository;

    public List<PlantWaterSupplyDTO> getWaterSupplyList(int plantId) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new EntityNotFoundException("[PlantWaterSupplyService] Plant Not Found Id : " + plantId));

        List<PlantWaterSupply> plantWaterSupplies = plantWaterSupplyRepository.findByPlant(plant);

        AtomicInteger listId = new AtomicInteger(1);

        return plantWaterSupplies.stream().map(plantWaterSupply -> {
            PlantWaterSupplyDTO dto = new PlantWaterSupplyDTO();

            dto.setListId(listId.getAndIncrement());
            dto.setSupplyDate(plantWaterSupply.getSupplyDate());
            dto.setSupplyTime(plantWaterSupply.getSupplyTime());

            return dto;
        }).collect(Collectors.toList());
    }

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
        alarmService.addAlarm("💧물 공급 완료", "물 공급이 성공적으로 완료되었습니다. \n 자세한 내용은 물 공급 기록을 확인해주세요!");
    }
}
