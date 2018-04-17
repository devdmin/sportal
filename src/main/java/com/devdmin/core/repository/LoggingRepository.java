package com.devdmin.core.repository;

import com.devdmin.core.model.Logging;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository class for <code>Logging</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Damian Ujma
 */
public interface LoggingRepository extends JpaRepository<Logging, Long> {
}
