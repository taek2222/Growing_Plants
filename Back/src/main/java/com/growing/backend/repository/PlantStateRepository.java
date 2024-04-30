package com.growing.backend.repository;

import com.growing.backend.entity.PlantState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantStateRepository extends JpaRepository<PlantState, Integer> {
}
