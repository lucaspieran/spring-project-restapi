package com.lucas.springsecurity.controller;

import com.lucas.springsecurity.controller.models.CreatePost;
import com.lucas.springsecurity.dto.PostDTO;
import com.lucas.springsecurity.entity.Post;
import com.lucas.springsecurity.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDTO> getAll() {
        List<Post> posts = postService.getAllPosts();
        List<PostDTO> postDTOs = posts.stream()
                .map(post -> new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getUser()))
                .collect(Collectors.toList());
        return postDTOs;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreatePost request) {
       try {
           postService.create(request);
           Map<String, Object> response = new HashMap<>();
           response.put("message", "User created successfully");

           return new ResponseEntity<>(response, HttpStatus.CREATED);
       } catch (Exception e) {
           Map<String, Object> response = new HashMap<>();
           response.put("message", "Not found");

           return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
       }
    }

}
