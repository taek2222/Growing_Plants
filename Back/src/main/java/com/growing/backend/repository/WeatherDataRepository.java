package com.growing.backend.repository;

import com.growing.backend.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Integer> {
    // 날짜 시간 기반 검색
    Optional<WeatherData> findByFcstDateAndFcstTime(LocalDate fcstDate, LocalTime fcstTime);
}
