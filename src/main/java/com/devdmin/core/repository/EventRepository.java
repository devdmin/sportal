package com.devdmin.core.repository;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository class for <code>Event</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Damian Ujma
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findBySportField_Id(Long Id);
    @Query(value = "SELECT e FROM Event e WHERE e.date BETWEEN ?1 AND ?2 OR e.endDate BETWEEN ?1 AND ?2 AND e.sportField = ?3")
    List<Event> findEventBetweenTwoDates(LocalDateTime date, LocalDateTime endDate, Long sportFieldId);
}
