package com.example.knowledgebase.services;

import com.example.knowledgebase.model.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    private Map<UUID, PostDTO> postMap;

    public PostServiceImpl() {
        this.postMap = new HashMap<>();

        PostDTO post1 = PostDTO.builder()
                .id(UUID.randomUUID())
                .title("Whatever")
                .content("Coolio")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        postMap.put(post1.getId(), post1);
    }

    @Override
    public List<PostDTO> listPosts() {
        return new ArrayList<>(postMap.values());
    }

    @Override
    public PostDTO saveNewPost(PostDTO post) {
        PostDTO savedPost = PostDTO.builder()
                .id(UUID.randomUUID())
                .title(post.getTitle())
                .content(post.getContent())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        postMap.put(savedPost.getId(), savedPost);

        return savedPost;
    }
}
