package com.growing.backend.controller;
import com.growing.backend.dto.response.PlantDTO;
import com.growing.backend.service.plant.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plant")
public class PlantController {
    private final PlantService plantService;

    // 식물 전체 요청
    @GetMapping("/all")
    public ResponseEntity<List<PlantDTO>> getPlantAll() {
        return ResponseEntity.ok().body(plantService.getPlant());
    }
}
