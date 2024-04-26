package com.growing.backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDataDTO {
    private double minTemp;  // 최저 기온
    private double maxTemp;  // 최고 기온
    private double currentTemp;  // 현재 기온
    private int weatherCode;
}
