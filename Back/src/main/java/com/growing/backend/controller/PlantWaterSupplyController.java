package com.growing.backend.controller;

import com.growing.backend.service.PlantWaterSupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plant/water-supply")
@RequiredArgsConstructor
public class PlantWaterSupplyController {
    private final PlantWaterSupplyService plantWaterSupplyService;

    @GetMapping("/{plantId}")
    public ResponseEntity<Void> toggleWater(@PathVariable("plantId") int plantId) {
        plantWaterSupplyService.addWaterSupplyList(plantId);
        return ResponseEntity.ok().build();
    }
}
