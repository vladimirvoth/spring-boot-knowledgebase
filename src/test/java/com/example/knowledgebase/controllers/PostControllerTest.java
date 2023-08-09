package com.example.knowledgebase.controllers;

import com.example.knowledgebase.controller.PostController;
import com.example.knowledgebase.entities.Post;
import com.example.knowledgebase.model.PostDTO;
import com.example.knowledgebase.services.PostService;
import com.example.knowledgebase.services.PostServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@WebMvcTest(PostController.class)
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    PostServiceImpl postServiceImpl;

    @BeforeEach
    void setUp() {
        postServiceImpl = new PostServiceImpl();
    }

    @Test
    void testListPosts() throws Exception {
        given(postService.listPosts()).willReturn(postServiceImpl.listPosts());

        mockMvc.perform(get(PostController.POST_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void testCreateNewPost() throws Exception {
        PostDTO post = postServiceImpl.listPosts().get(0);

        given(postService.saveNewPost(any(PostDTO.class))).willReturn(postServiceImpl.listPosts().get(0));

        mockMvc.perform(post(PostController.POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

}
