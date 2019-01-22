package com.devdmin.rest.controller;

import com.devdmin.core.model.Post;
import com.devdmin.core.model.User;
import com.devdmin.core.security.AccountUserDetails;
import com.devdmin.core.service.EventService;
import com.devdmin.core.service.util.PostList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/events")
public class PostController {

    private final EventService eventService;


    @Autowired
    public PostController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/{eventId}/addPost")
    @PreAuthorize("permitAll")
    public ResponseEntity<Post> addPost(@PathVariable Long eventId, @RequestBody Post sentPost){
        sentPost.setAuthor(getUser());
        return new ResponseEntity<Post>(eventService.addPost(eventId, sentPost), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId){
        return Optional.ofNullable(eventService.getPost(postId))
                .map(post -> {
                    return new ResponseEntity<Post>(post, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<Post>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{eventId}/posts")
    @PreAuthorize("permitAll")
    public ResponseEntity<PostList> getAllPosts(@PathVariable Long eventId){
        return Optional.ofNullable(eventService.getAllPostsByEvent(eventId))
                .map(posts -> {
                    return new ResponseEntity<PostList>(new PostList(posts), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<PostList>(HttpStatus.NOT_FOUND));
    }
    public User getUser() {
        AccountUserDetails user = (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser();
    }
}
