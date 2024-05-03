package com.growing.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "PLANT_INFO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantInfo {
    @Id
    @Column(name = "PLANT_ID")
    private int plantId; // 식물 ID

    @Column(name = "IMAGE")
    private String image; // 식물 이미지

    @Column(name = "DATE")
    private LocalDate date; // 식물 성장 시작 일자

    @Column(name = "LIGHT_THRESHOLD")
    private double lightThreshold; // 조도 기준치

    @Column(name = "LIGHT_STATUS")
    private boolean lightStatus; // 식물등 상태

    @Column(name = "SOIL_THRESHOLD")
    private double soilThreshold; // 습도 기준치

    @Column(name = "SUNLIGHT_DURATION")
    private int sunlightDuration; // 햇빛 비춘 시간

    @Column(name = "GROW_LIGHT_DURATION")
    private int growLightDuration; // 식물등 비춘 시간

    @OneToOne
    @MapsId
    @JoinColumn(name = "plant_id")
    private Plant plant;
}
