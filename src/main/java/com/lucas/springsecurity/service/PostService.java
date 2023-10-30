package com.lucas.springsecurity.service;

import com.lucas.springsecurity.controller.models.CreatePost;
import com.lucas.springsecurity.entity.Post;
import com.lucas.springsecurity.entity.User;
import com.lucas.springsecurity.repository.PostRepository;
import com.lucas.springsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post create(CreatePost post) {
        Optional<User> userOptional = userRepository.findById(post.getUserId());
        logger.info("create user {}");

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Post newPost = Post.builder()
                    .content(post.getContent())
                    .title(post.getTitle())
                    .user(user)
                    .build();
            logger.info("create user {}", newPost);

            return postRepository.save(newPost);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + post.getUserId());
        }
    }

     public void updatePost(Long postId, Post updatedPost) {
        Optional<Post> existingPostOptional = postRepository.findById(postId);

        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setContent(updatedPost.getContent());


            postRepository.save(existingPost);
        }
    }
}
