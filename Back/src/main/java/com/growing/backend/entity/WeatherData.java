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
    @Column(name = "DATA_NUMBER")
    private int dataNumber;

    @Column(name = "FCST_Date")
    private LocalDate fcstDate;

    @Column(name = "FCST_TIME")
    private LocalTime fcstTime;

    @Column(name = "TEMPERATURE")
    private int temperature;

    @Column(name = "HUMIDITY")
    private int humidity;

    @Column(name = "WEATHER_CODE")
    private int weatherCode;

    @Column(name = "RAIN")
    private int rain;

    public WeatherData(LocalDate fcstDate, LocalTime fcstTime, int temperature, int humidity, int weatherCode, int rain) {
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.temperature = temperature;
        this.humidity = humidity;
        this.weatherCode = weatherCode;
        this.rain = rain;
    }
}
