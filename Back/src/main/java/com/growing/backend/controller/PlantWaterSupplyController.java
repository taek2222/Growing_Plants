package com.growing.backend.controller;

import com.growing.backend.dto.response.PlantWaterSupplyDTO;
import com.growing.backend.service.plant.PlantWaterSupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plant/water-supply")
@RequiredArgsConstructor
public class PlantWaterSupplyController {
    private final PlantWaterSupplyService plantWaterSupplyService;

    // 물 공급 리스트 요청
    @GetMapping("/{plantId}")
    public ResponseEntity<List<PlantWaterSupplyDTO>> getWaterSupplyList(@PathVariable("plantId") int plantId) {
        List<PlantWaterSupplyDTO> plantWaterSupplyDTOS = plantWaterSupplyService.getWaterSupplyList(plantId);
        return ResponseEntity.ok(plantWaterSupplyDTOS);
    }

    // 물 공급 리스트 저장
    @PostMapping("/{plantId}")
    public ResponseEntity<Void> addWaterSupplyList(@PathVariable("plantId") int plantId) {
        plantWaterSupplyService.addWaterSupplyList(plantId);
        return ResponseEntity.ok().build();
    }
}
