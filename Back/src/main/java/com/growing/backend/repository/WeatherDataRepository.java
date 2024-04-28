package com.growing.backend.repository;

import com.growing.backend.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Integer> {
    Optional<WeatherData> findByFcstDateAndFcstTime(LocalDate fcstDate, LocalTime fcstTime);

    Optional<WeatherData> findByFcstDate(LocalDate date);
}
