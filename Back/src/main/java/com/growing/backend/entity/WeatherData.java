package com.growing.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "WEATHER_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATA_ID")
    private int dataId; // 데이터 번호

    @Column(name = "FCST_Date")
    private LocalDate fcstDate; // 날짜

    @Column(name = "FCST_TIME")
    private LocalTime fcstTime; // 시간

    @Column(name = "TEMPERATURE")
    private int temperature; // 온도(1시간)

    @Column(name = "HUMIDITY")
    private int humidity; // 습도

    @Column(name = "WEATHER_CODE")
    private int weatherCode; // 기상 코드

    @Column(name = "RAIN")
    private int rain; // 강수 확률
}
