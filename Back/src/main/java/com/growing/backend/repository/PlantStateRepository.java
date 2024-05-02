package com.growing.backend.repository;

import com.growing.backend.entity.PlantState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantStateRepository extends JpaRepository<PlantState, Integer> {
    List<PlantState> findAllByOrderByDateDescTimeDesc(Pageable pageable);
}
