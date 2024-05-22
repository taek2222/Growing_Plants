package com.growing.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlantSettingResponse {
    private PlantSetting plantSetting;
    private PlantInfoSetting plantInfoSetting;
    private PlantThresholdSetting plantThresholdSetting;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PlantSetting {
        private int plantId; // 식물 아이디
        private String plantName; // 식물 이름
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PlantInfoSetting {
        private int date; // 성장 일자
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PlantThresholdSetting {
        private double lightThreshold; // 조도 기준치
        private double soilThreshold; // 습도 기준치
        private double waterThreshold; // 물 기준치
    }
}