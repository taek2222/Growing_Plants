package com.growing.backend.controller;

import com.growing.backend.dto.PlantRequestDTO;
import com.growing.backend.entity.PlantState;
import com.growing.backend.service.PlantStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plant")
@RequiredArgsConstructor
public class PlantStateController {
    private final PlantStateService plantStateService;

    @GetMapping("/state")
    public PlantRequestDTO getPlantState() {
        return plantStateService.getPlantState();
    }

    @PostMapping("/state")
    public PlantState postPlantState(@RequestBody PlantRequestDTO plantRequestDTO) {
        return plantStateService.plantStateSave(plantRequestDTO);
    }
}
