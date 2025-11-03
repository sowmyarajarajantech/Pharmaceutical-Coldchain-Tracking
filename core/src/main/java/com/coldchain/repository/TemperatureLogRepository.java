package com.coldchain.repository;

import com.coldchain.model.TemperatureLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureLogRepository extends JpaRepository<TemperatureLog, Long> {}