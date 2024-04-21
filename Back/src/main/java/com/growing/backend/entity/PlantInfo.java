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
    private int plantId;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "DATE")
    private LocalDate date;

    @OneToOne
    @MapsId
    @JoinColumn(name = "plant_id")
    private Plant plant;
}
