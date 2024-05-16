package com.growing.backend.controller;

import com.growing.backend.service.plant.PlantWaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plant")
public class PlantWaterController {
    private final PlantWaterService plantWaterService;

    @GetMapping("/water/{plantId}")
    public ResponseEntity<String> toggleWater(@PathVariable("plantId") int plantId) {
        String response = plantWaterService.toggleWater(plantId);
        return ResponseEntity.ok(response);
    }
}