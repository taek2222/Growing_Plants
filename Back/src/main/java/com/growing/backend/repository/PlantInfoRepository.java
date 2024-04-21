package com.growing.backend.repository;

import com.growing.backend.entity.PlantInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantInfoRepository extends JpaRepository<PlantInfo, Integer> {
}
