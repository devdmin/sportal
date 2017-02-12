package com.devdmin.core.repository;

import com.devdmin.core.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findBySportField_Id(Long Id);
}
