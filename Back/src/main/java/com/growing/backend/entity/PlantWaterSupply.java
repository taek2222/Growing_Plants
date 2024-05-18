package com.growing.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "PLANT_WATER_SUPPLY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantWaterSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIST_ID")
    private int listId;

    @Column(name = "SUPPLY_DATE")
    private LocalDate supplyDate;

    @Column(name = "SUPPLY_TIME")
    private LocalTime supplyTime;

    @ManyToOne
    @JoinColumn(name = "PLAINT_ID")
    private Plant plant;
}
