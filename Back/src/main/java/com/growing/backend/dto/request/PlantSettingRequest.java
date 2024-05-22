package com.growing.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
// 식물 상세 정보 PATCH(Front->Back) 요청 DTO
public class PlantSettingRequest {
    private int plantId; // 식물 아이디
    private String plantName; // 식물 이름
    private LocalDate date; // 성장 일자
    private double lightThreshold; // 조도 기준치
    private double soilThreshold; // 습도 기준치
    private double waterThreshold; // 물 기준치
    private int sunLightMax; // // 햇빛 + 식물등 시간 최대치
}