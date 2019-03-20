package com.devdmin.rest.controller.dto.converter.dto;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.Post;
import com.devdmin.core.model.User;
import com.devdmin.rest.controller.dto.EventDto;
import com.devdmin.rest.controller.dto.PostDto;
import com.devdmin.rest.controller.dto.UserDto;
import com.devdmin.rest.controller.dto.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DefaultEventDtoConverter implements BaseConverter<EventDto, Event> {

    @Autowired
    private BaseConverter<UserDto, User> userConverter;

    @Autowired
    private BaseConverter<PostDto, Post> postConverter;

    @Override
    public Event convert(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setDate(eventDto.getDate());
        event.setEndDate(eventDto.getEndDate());
        event.setMaxAge(eventDto.getMaxAge());
        event.setMinAge(eventDto.getMinAge());
        event.setUsers(userConverter.convertAll(eventDto.getUsers()));
        event.setMaxMembers(eventDto.getMaxMembers());
        event.setGender(eventDto.getGender());
        event.setPosts(postConverter.convertAll(eventDto.getPosts()));
        return event;
    }
}
