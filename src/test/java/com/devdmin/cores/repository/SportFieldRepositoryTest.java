package com.devdmin.cores.repository;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.repository.SportFieldRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class SportFieldRepositoryTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SportFieldRepository sportFieldRepository;

    @Test
    public void testFind() throws Exception{
        SportField sportField = new SportField();
        sportField.setLat(24.2444);
        sportField.setLng(54.2555);
        sportField.setType(SportFieldType.BASKETBALL);
        sportField.setVerified(false);
        sportField.setAddingDate(LocalDate.now());
        Long id = this.entityManager.persistAndGetId(sportField, Long.class);
        SportField foundSportField = sportFieldRepository.findOne(id);
        assertEquals(foundSportField.getId(), id);

    }

    @Test
    public void testDelete() throws Exception{
        SportField sportField = new SportField();
        sportField.setLat(24.2444);
        sportField.setLng(54.2555);
        sportField.setType(SportFieldType.BASKETBALL);
        sportField.setVerified(false);
        sportField.setAddingDate(LocalDate.now());
        Long id = this.entityManager.persistAndGetId(sportField, Long.class);
        sportFieldRepository.delete(id);
        SportField foundSportField = sportFieldRepository.findOne(id);
        assertNull(foundSportField);
    }
}
