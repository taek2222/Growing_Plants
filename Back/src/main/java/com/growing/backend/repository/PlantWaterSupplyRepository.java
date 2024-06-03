package com.growing.backend.repository;

import com.growing.backend.entity.Plant;
import com.growing.backend.entity.PlantWaterSupply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PlantWaterSupplyRepository extends JpaRepository<PlantWaterSupply, Integer> {
    List<PlantWaterSupply> findByPlant(Plant plant);
    Integer countByPlantAndSupplyDate(Plant plant, LocalDate today);
}
