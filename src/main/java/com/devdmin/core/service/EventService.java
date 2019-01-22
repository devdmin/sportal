package com.devdmin.core.service;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.Post;
import com.devdmin.core.model.User;
import com.devdmin.core.service.util.PostList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventService {
    Event save(Event event);

    Event find(Long id);

    Event update(Long id, Event event);

    Event delete(Long id);

    Event add(Event event, Long sportFieldId);

    Event join(Event event, User user);

    Event signOut(Event event, User user);

    List<Event> findBySportFieldId(Long sportFieldId);

    List<Event> findAll();

    Set<Post> getAllPostsByEvent(Long id);

    Post addPost(Long id, Post post);

    Post getPost(Long id);
}
