package com.growing.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PLANT_WATER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantWater {
    @Id
    @Column(name = "PLANT_ID")
    private int plantId; // 식물 ID

    @Column(name = "WATER_TOGGLE")
    private boolean waterToggle; // 물 주기 옵션 활성화

    @OneToOne
    @MapsId
    @JoinColumn(name = "plant_id")
    private Plant plant;
}