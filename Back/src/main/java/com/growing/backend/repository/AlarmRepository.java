package com.growing.backend.repository;

import com.growing.backend.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    boolean existsByReadFlag(boolean readFlag);
}
