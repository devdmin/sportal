package com.devdmin.rest.controller;

import com.devdmin.core.model.Post;
import com.devdmin.core.model.User;
import com.devdmin.core.security.AccountUserDetails;
import com.devdmin.core.service.EventService;
import com.devdmin.core.service.util.PostDtoList;
import com.devdmin.rest.controller.dto.PostDto;
import com.devdmin.rest.controller.dto.converter.BaseConverter;
import javafx.geometry.Pos;
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
    private BaseConverter<Post, PostDto> postDomainConverter;

    @Autowired
    private BaseConverter<PostDto, Post> postDtoConverter;

    @Autowired
    public PostController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/{eventId}/addPost")
    @PreAuthorize("permitAll")
    public ResponseEntity<PostDto> addPost(@PathVariable Long eventId, @RequestBody PostDto sentPost){
        Post post = postDtoConverter.convert(sentPost);
        post.setAuthor(getUser());
        return new ResponseEntity<PostDto>(postDomainConverter.convert(eventService.addPost(eventId, post)), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId){
        return Optional.ofNullable(eventService.getPost(postId))
                .map(post -> {
                    return new ResponseEntity<PostDto>(postDomainConverter.convert(post), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<PostDto>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{eventId}/posts")
    @PreAuthorize("permitAll")
    public ResponseEntity<PostDtoList> getAllPosts(@PathVariable Long eventId){
        return Optional.ofNullable(eventService.getAllPostsByEvent(eventId))
                .map(posts -> {
                    return new ResponseEntity<PostDtoList>(new PostDtoList(postDomainConverter.convertAll(posts)), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<PostDtoList>(HttpStatus.NOT_FOUND));
    }
    public User getUser() {
        AccountUserDetails user = (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser();
    }
}
