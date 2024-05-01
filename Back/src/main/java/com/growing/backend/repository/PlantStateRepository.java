package com.growing.backend.repository;

import com.growing.backend.entity.PlantState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantStateRepository extends JpaRepository<PlantState, Integer> {
    List<PlantState> findAllByOrderByDateDescTimeDesc(Pageable pageable);
}
