package com.growing.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "DATE_TEMPERATURE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateTemperature {
    @Id
    @Column(name = "DATE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dateId;

    @Column(name = "FCST_Date")
    private LocalDate fcstDate;

    @Column(name = "TMX")
    private double tmx;

    @Column(name = "tmn")
    private double tmn;
}
