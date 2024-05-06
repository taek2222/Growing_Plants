package com.growing.backend.repository;

import com.growing.backend.entity.WeatherTemperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherTemperatureRepository extends JpaRepository<WeatherTemperature, Integer> {
    // 날짜 기반 검색
    Optional<WeatherTemperature> findByFcstDate(LocalDate fcstDate);
}
