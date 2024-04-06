package com.growing.backend.controller;

import com.growing.backend.dto.PlantDTO;
import com.growing.backend.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plant")
public class PlantController {
    private final PlantService plantService;

    @GetMapping("/all")
    public List<PlantDTO> getPlantAll() {
        return plantService.getPlantAllSearch();
    }
}
