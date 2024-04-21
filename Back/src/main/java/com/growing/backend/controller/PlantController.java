package com.growing.backend.controller;

import com.growing.backend.dto.PlantDTO;
import com.growing.backend.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/name-patch")
    public String testPost(@RequestBody PlantDTO plantDTO) {
        System.out.println(plantDTO.getPlantId());
        System.out.println(plantDTO.getPlantName());
        plantService.updatePlant(plantDTO);
        return plantDTO.getPlantName();
    }
}
