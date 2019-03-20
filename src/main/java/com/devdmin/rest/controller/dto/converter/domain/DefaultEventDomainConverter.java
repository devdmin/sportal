package com.devdmin.rest.controller.dto.converter.domain;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.Post;
import com.devdmin.core.model.User;
import com.devdmin.rest.controller.dto.EventDto;
import com.devdmin.rest.controller.dto.PostDto;
import com.devdmin.rest.controller.dto.UserDto;
import com.devdmin.rest.controller.dto.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultEventDomainConverter implements BaseConverter<Event, EventDto> {

    @Autowired
    private BaseConverter<Post, PostDto> postDomainConverter;
    @Autowired
    private BaseConverter<User, UserDto> userDomainConverter;
    @Override
    public EventDto convert(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setDate(event.getDate());
        eventDto.setId(event.getId());
        eventDto.setGender(event.getGender());
        eventDto.setMaxAge(event.getMaxAge());
        eventDto.setMinAge(event.getMinAge());
        eventDto.setMaxMembers(event.getMaxMembers());
        eventDto.setEndDate(event.getEndDate());
        eventDto.setPosts(postDomainConverter.convertAll(event.getPosts()));
        eventDto.setUsers(userDomainConverter.convertAll(event.getUsers()));
        return eventDto;
    }
}
