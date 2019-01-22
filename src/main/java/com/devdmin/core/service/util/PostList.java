package com.devdmin.core.service.util;

import com.devdmin.core.model.Post;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostList {
    private Set<Post> posts = new HashSet<>();

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public PostList(Set<Post> posts) {
        this.posts = posts;
    }
}
