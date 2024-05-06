package com.growing.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PLANT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plant {
    @Id
    @Column(name = "PLANT_ID")
    private int plantId; // 식물 ID

    @Column(name = "PLANT_NAME")
    private String plantName; // 식물 이름
}
