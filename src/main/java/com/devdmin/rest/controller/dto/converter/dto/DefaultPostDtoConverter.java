package com.devdmin.rest.controller.dto.converter.dto;

import com.devdmin.core.model.Post;
import com.devdmin.core.model.User;
import com.devdmin.rest.controller.dto.PostDto;
import com.devdmin.rest.controller.dto.UserDto;
import com.devdmin.rest.controller.dto.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultPostDtoConverter implements BaseConverter<PostDto, Post> {

    @Autowired
    private BaseConverter<UserDto, User> userDtoConverter;

    public Post convert(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setText(postDto.getText());
        if(Optional.ofNullable(postDto.getAuthor()).isPresent()) {
            post.setAuthor(userDtoConverter.convert(postDto.getAuthor()));
        }
        post.setDate(postDto.getDate());
        return post;
    }
}
