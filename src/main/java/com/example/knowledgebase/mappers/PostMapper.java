package com.example.knowledgebase.mappers;

import com.example.knowledgebase.entities.Post;
import com.example.knowledgebase.model.PostDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {
    Post postDtoToPost(PostDTO dto);

    PostDTO postToPostDto(Post post);
}
