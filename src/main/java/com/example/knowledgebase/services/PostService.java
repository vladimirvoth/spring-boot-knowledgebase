package com.example.knowledgebase.services;

import com.example.knowledgebase.model.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> listPosts();

    PostDTO saveNewPost(PostDTO post);
}
