package com.devdmin;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.repository.EventRepository;
import com.devdmin.core.repository.SportFieldRepository;
import com.devdmin.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RunAtStart {
    private final UserRepository userRepository;
    private final SportFieldRepository sportFieldRepository;
    private final EventRepository eventRepository;

    @Autowired
    public RunAtStart(UserRepository userRepository, SportFieldRepository sportFieldRepository
                        ,EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.sportFieldRepository = sportFieldRepository;
        this.eventRepository = eventRepository;
    }

    @PostConstruct
    public void runAtStart() {
        User user = new User();
        user.setUsername("johnsoon9");
        user.setPassword("111111111");
        user.setEmail("johnsoon9@email.com");
        user.setSignUpDate(LocalDate.of(2016,11,19));

        SportField sportField1 = new SportField();
        sportField1.setCoords("23'.343434 43'.24434");
        sportField1.setType("Football");
        Event event1 = new Event();
        event1.setDate(LocalDateTime.of(2016,11,19,20,00));
        event1.setSportField(sportField1);

        List<User> users = new ArrayList<>();
        users.add(user);
        event1.setUsers(users);
        eventRepository.save(event1);
        sportFieldRepository.save(sportField1);
        userRepository.save(user);


        User user1 =  userRepository.findByUsername("johnsoon9");
        System.out.println(user1);
    }
}
