package com.growing.backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDataDTO {
    private int day; // 오늘(1), 내일(2), 모레(3)
    private int time; // 시간
    private double maxTemp;  // 최고 기온
    private double minTemp;  // 최저 기온
    private int currentTemp;  // 현재 기온
    private int humidity; // 습도
    private int weatherCode; // 날씨 상태 코드
    private int rain; // 강수 확률
}
