package com.growing.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PLANT_THRESHOLD")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantThreshold {
    @Id
    @Column(name = "PLANT_ID")
    private int plantId; // 식물 ID

    @Column(name = "LIGHT_THRESHOLD")
    private double lightThreshold; // 조도 기준치

    @Column(name = "SOIL_THRESHOLD")
    private double soilThreshold; // 습도 기준치

    @Column(name = "WATER_THRESHOLD")
    private double waterThreshold; // 물 기준치

    @Column(name = "WATER_FLAG")
    private boolean waterFlag; // 물 알람 Flag [00:00 시 초기화]

    @Column(name = "SUN_LIGHT_MAX")
    private int sunLightMax; // 햇빛 + 식물등 시간 최대치

    @OneToOne
    @MapsId
    @JoinColumn(name = "PLANT_ID")
    private Plant plant;
}
