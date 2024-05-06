package com.growing.backend.repository;

import com.growing.backend.entity.PlantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantInfoRepository extends JpaRepository<PlantInfo, Integer> {
}
