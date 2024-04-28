package com.growing.backend.repository;

import com.growing.backend.entity.DateTemperature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DateTemperatureRepository extends JpaRepository<DateTemperature, Integer> {
    Optional<DateTemperature> findByFcstDate(LocalDate fcstDate);
}
