package com.growing.backend.repository;

import com.growing.backend.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Integer> {
}
