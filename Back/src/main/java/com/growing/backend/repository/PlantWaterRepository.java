package com.growing.backend.repository;

import com.growing.backend.entity.PlantWater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantWaterRepository extends JpaRepository<PlantWater, Integer> {
}