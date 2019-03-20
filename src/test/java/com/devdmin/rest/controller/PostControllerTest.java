package com.devdmin.rest.controller;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.Post;
import com.devdmin.core.security.AccountUserDetails;
import com.devdmin.core.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class PostControllerTest {
    @InjectMocks
    private PostController controller;

    @Mock
    private EventService service;

    @Mock
    private AccountUserDetails applicationUser;

    private MockMvc mockMvc;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }
    @Test
    public void addPost() throws Exception{

        AccountUserDetails applicationUser = mock(AccountUserDetails.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        when(service.find(any(Long.class))).thenReturn(new Event());
        when((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);

        mockMvc.perform(post("/api/events/2/addPost")
                .content("{\"text\":\"example text\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void getPost() throws Exception {
        Post post = new Post();
        post.setText("text");
        when(service.getPost(any(Long.class))).thenReturn(post);

        mockMvc.perform(get("/api/events/post/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text", is(post.getText())))
                .andExpect(status().isOk());
    }


    @Test
    public void getPosts() throws Exception {

       Set<Post> posts = new HashSet<Post>();
        Post post = new Post();
        post.setText("text");
        posts.add(post);


        when(service.getAllPostsByEvent(any(Long.class))).thenReturn(posts);

        mockMvc.perform(get("/api/events/2/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.posts[*].text", hasItems(post.getText())))
                .andExpect(status().isOk());
    }
}
