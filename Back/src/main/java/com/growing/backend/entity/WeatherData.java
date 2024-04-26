package com.growing.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "WEATHER_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATA_NUMBER")
    private int dataNumber;

    @Column(name = "FCST_Date")
    private String fcstDate;

    @Column(name = "FCST_TIME")
    private String fcstTime;

    @Column(name = "TEMPERATURE")
    private int temperature;

    @Column(name = "HUMIDITY")
    private int humidity;

    @Column(name = "WEATHER_CODE")
    private int weatherCode;

    @Column(name = "RAIN")
    private int rain;

    public WeatherData(String fcstDate, String fcstTime, int temperature, int humidity, int weatherCode, int rain) {
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.temperature = temperature;
        this.humidity = humidity;
        this.weatherCode = weatherCode;
        this.rain = rain;
    }
}
