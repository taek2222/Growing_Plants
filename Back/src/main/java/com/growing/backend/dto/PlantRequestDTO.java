package com.growing.backend.dto;

import lombok.*;

@Getter
@Setter
public class PlantRequestDTO {
    private double lightIntensity; // 조도
    private double airTemperature; // 대기 온도
    private double airHumidity; // 대기 습도
    private double soilMoisture1; // 토양 습도 (식물 1)
    private double soilMoisture2; // 토양 습도 (식물 2)
}
