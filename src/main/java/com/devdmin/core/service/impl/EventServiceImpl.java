package com.devdmin.core.service.impl;

import com.devdmin.core.businessvalidator.BusinessValidator;
import com.devdmin.core.model.Event;
import com.devdmin.core.model.Post;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.repository.EventRepository;
import com.devdmin.core.repository.PostRespository;
import com.devdmin.core.repository.SportFieldRepository;
import com.devdmin.core.repository.UserRepository;
import com.devdmin.core.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SportFieldRepository sportFieldRepository;

    @Autowired
    private PostRespository postRespository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessValidator<Event,User> validator;

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event find(Long id) {
        return eventRepository.findOne(id);
    }

    @Override
    public Event update(Long id, Event event) {
        Event foundEvent = eventRepository.findOne(id);
        foundEvent.update(event);
        return eventRepository.save(foundEvent);
    }

    @Override
    public Event delete(Long id) {
        eventRepository.delete(id);
        return find(id);
    }

    @Override
    public Event add(Event event, Long sportFieldId) {
        event.setAddingDate(LocalDate.now());
        SportField foundSportField = sportFieldRepository.findOne(sportFieldId);
        User foundUser = userRepository.findByUsername(event.getEventAuthor().getUsername());
        event.setSportField(foundSportField);

        if(validator.validateAdding(event, foundUser)) {


            Set<Event> events = Optional.ofNullable(foundSportField.getEvents())
                    .orElse(new HashSet<Event>());

            Set<Event> userEvents = Optional.ofNullable(foundUser.getOwnEvents())
                    .orElse(new HashSet<Event>());

            events.add(event);
            userEvents.add(event);
            event.setEventAuthor(foundUser);
            event.setSportField(foundSportField);

            Event savedEvent = eventRepository.save(event);
            savedEvent = join(savedEvent, foundUser);
            return savedEvent;
        }
        return null;
    }

    @Override
    public Event join(Event event, User user) {
        Set<User> users = Optional.ofNullable(event.getUsers())
                .orElse(new HashSet<User>());
        Set<Event> events = Optional.ofNullable(user.getEvents())
                .orElse(new HashSet<Event>());

        users.add(user);
        event.setUsers(users);
        events.add(event);
        user.setEvents(events);
        Event savedEvent = eventRepository.save(event);
        return savedEvent;
    }

    @Override
    public Event signOut(Event event, User user) {
        Set<User> users = event.getUsers();
        Set<Event> events = user.getEvents();

        events.remove(event);
        users.remove(user);

        return eventRepository.save(event);
    }

    public List<Event> findBySportFieldId(Long sportFieldId){
        return eventRepository.findBySportField_Id(sportFieldId);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Set<Post> getAllPostsByEvent(Long id) {
        Set<Post> posts = eventRepository.findOne(id).getPosts();
        return posts;
    }

    @Override
    public Post addPost(Long id, Post post) {
        Event event = find(id);
        Set<Post> posts = Optional.ofNullable(event.getPosts())
                .orElse(new HashSet<Post>());
        posts.add(post);
        post.setEvent(event);
        postRespository.save(post);
        return post;
    }

    @Override
    public Post getPost(Long id) {
        Post post = postRespository.findOne(id);
        return post;
    }


}
