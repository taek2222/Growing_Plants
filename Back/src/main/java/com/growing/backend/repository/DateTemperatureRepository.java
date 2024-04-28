package com.growing.backend.repository;

import com.growing.backend.entity.WeatherTemperature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DateTemperatureRepository extends JpaRepository<WeatherTemperature, Integer> {
    Optional<WeatherTemperature> findByFcstDate(LocalDate fcstDate);
}
