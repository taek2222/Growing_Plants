package com.growing.backend.dto.response;

import lombok.*;

@Getter
@Setter
// 기상청 정보 GET(Back -> Front) 요청 DTO
public class WeatherDataDTO {
    private int month; // 월
    private int day; // 일
    private int time; // 시간
    private double maxTemp;  // 최고 기온
    private double minTemp;  // 최저 기온
    private int currentTemp;  // 현재 기온
    private int humidity; // 습도
    private int weatherCode; // 날씨 상태 코드
    private int rain; // 강수 확률
}
