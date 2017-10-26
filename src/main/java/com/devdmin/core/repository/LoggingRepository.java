package com.devdmin.core.repository;

import com.devdmin.core.model.Logging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggingRepository extends JpaRepository<Logging, Long> {
}
