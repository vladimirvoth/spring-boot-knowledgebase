package com.example.knowledgebase.controller;

import com.example.knowledgebase.model.PostDTO;
import com.example.knowledgebase.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {
    public static final String POST_PATH = "/api/v1/post";

    private final PostService postService;

    private final SimpMessagingTemplate template;

    @GetMapping(POST_PATH)
    public List<PostDTO> listPosts() {
        return postService.listPosts();
    }

    @PostMapping(POST_PATH)
    public ResponseEntity savePost(@Validated @RequestBody PostDTO post) {
        log.info("das ist der Body" + post.toString());

        PostDTO savedPost = postService.saveNewPost(post);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", POST_PATH + "/" + savedPost.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) {
        System.out.println("Received: " + message);
        return "Hello from Spring!";
    }

    @GetMapping("/send")
    public String sendMsg(@RequestParam String message) {
        template.convertAndSend("/topic/greetings", message);
        return "Message sent!";
    }
}
