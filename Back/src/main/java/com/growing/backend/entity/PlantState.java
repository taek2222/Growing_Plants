package com.growing.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "PLANT_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plantDataId; // 데이터 번호

    @Column(name = "DATA")
    private LocalDate date; // 데이터 날짜

    @Column(name = "TIME")
    private LocalTime time; // 데이터 시간

    @Column(name = "LIGHTINTENSITY")
    private double lightIntensity; // 조도

    @Column(name = "AIRTEMPERATURE")
    private double airTemperature; // 대기 온도

    @Column(name = "AIRHUMIDITY")
    private double airHumidity; // 대기 습도

    @Column(name = "SOILMOISTURE1")
    private double soilMoisture1; // 토양 습도 (식물 1)

    @Column(name = "SOILMOISTURE2")
    private double soilMoisture2; // 토양 습도 (식물 2)
}
