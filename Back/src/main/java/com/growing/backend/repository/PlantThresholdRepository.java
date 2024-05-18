package com.growing.backend.repository;

import com.growing.backend.entity.PlantThreshold;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantThresholdRepository extends JpaRepository<PlantThreshold, Integer> {
}