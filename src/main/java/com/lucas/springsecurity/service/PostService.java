package com.lucas.springsecurity.service;

import com.lucas.springsecurity.controller.models.CreatePost;
import com.lucas.springsecurity.entity.Post;
import com.lucas.springsecurity.entity.User;
import com.lucas.springsecurity.repository.PostRepository;
import com.lucas.springsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post create(CreatePost post) {
        Optional<User> userOptional = userRepository.findById(post.getUserId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Post newPost = Post.builder()
                    .content(post.getContent())
                    .title(post.getTitle())
                    .user(user)
                    .build();

            return postRepository.save(newPost);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + post.getUserId());
        }
    }
}
