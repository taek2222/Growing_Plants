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
public class WeatherTemperature {
    @Id
    @Column(name = "DATE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dateId; // 데이터 번호

    @Column(name = "FCST_Date")
    private LocalDate fcstDate; // 날짜

    @Column(name = "TMX")
    private double tmx; // 최고 온도

    @Column(name = "TMN")
    private double tmn; // 최저 온도
}
