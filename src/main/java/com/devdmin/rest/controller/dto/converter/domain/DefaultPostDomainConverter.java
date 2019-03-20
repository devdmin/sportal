package com.devdmin.rest.controller.dto.converter.domain;

import com.devdmin.core.model.Post;
import com.devdmin.core.model.User;
import com.devdmin.rest.controller.dto.PostDto;
import com.devdmin.rest.controller.dto.UserDto;
import com.devdmin.rest.controller.dto.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultPostDomainConverter implements BaseConverter<Post, PostDto> {

    @Autowired
    private BaseConverter<User, UserDto> userDomainConverter;

    @Override
    public PostDto convert(Post post) {
        PostDto postDto = new PostDto();
        postDto.setText(post.getText());
        if(Optional.ofNullable(post.getAuthor()).isPresent())
        postDto.setAuthor(userDomainConverter.convert(post.getAuthor()));
        postDto.setDate(post.getDate());
        return postDto;
    }
}
