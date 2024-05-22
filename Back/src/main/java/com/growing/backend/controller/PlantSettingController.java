package com.growing.backend.controller;

import com.growing.backend.dto.request.PlantSettingRequest;
import com.growing.backend.dto.response.PlantSettingResponse;
import com.growing.backend.service.PlantSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plant")
public class PlantSettingController {
    private final PlantSettingService plantSettingService;

    // 식물 설정 정보 요청
    @GetMapping("/information-get/{plant_id}")
    public ResponseEntity<PlantSettingResponse> getPlantSetting(@PathVariable("plant_id") int plantId) {
        return new ResponseEntity<>(plantSettingService.getPlantSetting(plantId), HttpStatus.OK);
    }

    // 식물 설정 변경
    @PatchMapping("/information-patch/{plant_id}")
    public ResponseEntity<PlantSettingRequest> updatePlantSetting(@PathVariable("plant_id") int plantId, @RequestBody PlantSettingRequest plantSettingRequest) {
        plantSettingService.updatePlantSetting(plantId, plantSettingRequest);
        return new ResponseEntity<>(plantSettingRequest, HttpStatus.OK);
    }
}