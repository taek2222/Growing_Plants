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
    @Column(name = "plant_id")
    private int plantId;

    @Column(name = "plant_name")
    private String plantName;
}
