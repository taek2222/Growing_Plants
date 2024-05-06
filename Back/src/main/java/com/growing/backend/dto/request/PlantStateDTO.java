package com.growing.backend.dto.request;

import lombok.*;

@Getter
@Setter
// 식물 센서 값 POST(Software->Back) 요청 DTO
public class PlantStateDTO {
    private double lightIntensity; // 조도
    private double airTemperature; // 대기 온도
    private double airHumidity; // 대기 습도
    private double soilMoisture1; // 토양 습도 (식물 1)
    private double soilMoisture2; // 토양 습도 (식물 2)
    private boolean lightStatus1; // 식물등 상태 (식물 1)
    private boolean lightStatus2; // 식물등 상태 (식물 2)
}
