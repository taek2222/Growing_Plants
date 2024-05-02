package com.growing.backend.service.plant;

import com.growing.backend.dto.request.PlantStateDTO;
import com.growing.backend.entity.PlantState;
import com.growing.backend.repository.PlantStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StateReadService {
    private final PlantStateRepository plantStateRepository;

    // 식물 센서 측정 값 전달 (대기 온도, 대기 습도)
    public PlantStateDTO getPlantState() {

        Pageable topOne = PageRequest.of(0, 1);
        PlantState latestPlantState = plantStateRepository.findAllByOrderByDateDescTimeDesc(topOne).get(0);

        PlantStateDTO DTO = new PlantStateDTO();
        DTO.setAirTemperature(latestPlantState.getAirTemperature());
        DTO.setAirHumidity(latestPlantState.getAirHumidity());

        return DTO;
    }
}
