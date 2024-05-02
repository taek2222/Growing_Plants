package com.growing.backend.controller;

import com.growing.backend.dto.request.PlantStateDTO;
import com.growing.backend.entity.PlantState;
import com.growing.backend.service.plant.PlantStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plant")
@RequiredArgsConstructor
public class PlantStateController {
    private final PlantStateService plantStateService;

    // 식물 센서 측정 값 요청
    @GetMapping("/state")
    public PlantStateDTO getPlantState() {
        return plantStateService.getPlantState();
    }

    // 식물 센서 측정 값 저장
    // 조도 | 대기 온도 | 대기 습도 | 토양 습도 (식물 1) | 토양 습도 (식물 2) | 식물등 상태 (식물 1) | 식물등 상태 (식물 2)
    @PostMapping("/state")
    public PlantState postPlantState(@RequestBody PlantStateDTO plantStateRequestDTO) {
        return plantStateService.savePlantState(plantStateRequestDTO);
    }
}
