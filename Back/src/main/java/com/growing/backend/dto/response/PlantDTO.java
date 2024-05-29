package com.growing.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 식물 정보 GET(Back -> Front) 요청 DTO
public class PlantDTO {
    // Plant
    private int plantId; // 아이디
    private String plantName; // 이름

    // PlantInfo
    private String image; // 이미지
    private int date; // 성장 일자
    private double lightThreshold; // 조도 기준치
    private boolean lightStatus; // 식물등 상태
    private double soilThreshold; // 습도 기준치
    private double waterThreshold; // 물 기준치
    private int sunlightDuration; // 햇빛 비춘 시간
    private int growLightDuration; // 식물등 비춘 시간
    private boolean newAlarm; // 새로운 알람
}