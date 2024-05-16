package com.growing.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
// 식물 상세 정보 PATCH(Front->Back) 요청 DTO
public class PlantInfoDTO {
    private int id; // 식물 아이디
    private String name; // 식물 이름
    private LocalDate date; // 식물 성장 일자
    private double lightThreshold; // 조도 기준치
    private double soilThreshold; // 습도 기준치
    private double waterThreshold; // 물 기준치
}