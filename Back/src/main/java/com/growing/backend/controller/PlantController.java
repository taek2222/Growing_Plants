package com.growing.backend.controller;

import com.growing.backend.dto.PlantDTO;
import com.growing.backend.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plant")
public class PlantController {
    private final PlantService plantService;

    // 식물 전체 요청
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getPlantAll() {
        return ResponseEntity.ok().body(plantService.getPlantAllSearch());
    }

    // 식물 이름 변경 요청
    @PatchMapping("/name-patch")
    public String namePatch(@RequestBody PlantDTO plantDTO) {
        plantService.updatePlant(plantDTO);
        return plantDTO.getPlantName();
    }
}
