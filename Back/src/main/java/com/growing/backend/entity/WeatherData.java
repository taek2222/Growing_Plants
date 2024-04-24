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

    @Column(name = "BASE_Date")
    private String baseDate;

    @Column(name = "BASE_TIME")
    private String baseTime;

    @Column(name = "TEMPERATURE")
    private int temperature;

    @Column(name = "HUMIDITY")
    private int humidity;

    @Column(name = "WEATHER_CODE")
    private int weatherCode;

    @Column(name = "RAIN")
    private int rain;

    public WeatherData(String baseDate, String baseTime, int temperature, int humidity, int weatherCode, int rain) {
        this.baseDate = baseDate;
        this.baseTime = baseTime;
        this.temperature = temperature;
        this.humidity = humidity;
        this.weatherCode = weatherCode;
        this.rain = rain;
    }
}
