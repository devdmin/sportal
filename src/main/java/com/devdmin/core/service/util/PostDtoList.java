package com.devdmin.core.service.util;

import com.devdmin.core.model.Post;
import com.devdmin.rest.controller.dto.PostDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostDtoList {
    private Set<PostDto> posts = new HashSet<>();

    public Set<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostDto> posts) {
        this.posts = posts;
    }

    public PostDtoList(Set<PostDto> posts) {
        this.posts = posts;
    }
}
