package com.example.knowledgebase.controllers;

import com.example.knowledgebase.controller.PostController;
import com.example.knowledgebase.entities.Post;
import com.example.knowledgebase.model.PostDTO;
import com.example.knowledgebase.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostControllerIT {
    @Autowired
    PostController postController;

    @Autowired
    PostRepository postRepository;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testListPosts() {
        List<PostDTO> dtos = postController.listPosts();

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewPostTest() {
        PostDTO postDTO = PostDTO.builder()
                .title("Test")
                .content("Test Content")
                .build();

        ResponseEntity responseEntity = postController.savePost(postDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Post post = postRepository.findById(savedUUID).get();
        assertThat(post).isNotNull();
    }
}
