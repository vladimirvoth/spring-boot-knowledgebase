package com.example.knowledgebase.services;

import com.example.knowledgebase.mappers.PostMapper;
import com.example.knowledgebase.model.PostDTO;
import com.example.knowledgebase.repositories.PostRepository;
import lombok.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class PostServiceJPA implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;


    @Override
    public List<PostDTO> listPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::postToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO saveNewPost(PostDTO post) {
        return postMapper.postToPostDto(postRepository.save(postMapper.postDtoToPost(post)));
    }
}
