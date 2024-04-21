package com.growing.backend.service;

import com.growing.backend.dto.PlantDTO;
import com.growing.backend.entity.Plant;
import com.growing.backend.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;

    public List<PlantDTO> getPlantAllSearch() {
        List<Plant> plants = plantRepository.findAll();
        return plants.stream()
                .map(plant -> {
                    PlantDTO dto = new PlantDTO();
                    dto.setPlantId(plant.getPlantId());
                    dto.setPlantName(plant.getPlantName());
                    return dto;
                }).collect(Collectors.toList());
    }

    public void updatePlant(PlantDTO dto) {
        Plant plant = plantRepository.findById(dto.getPlantId()).orElseThrow();
        plant.setPlantName(dto.getPlantName());
        plantRepository.save(plant);
    }
}
