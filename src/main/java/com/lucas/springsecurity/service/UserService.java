package com.lucas.springsecurity.service;

import com.lucas.springsecurity.entity.User;
import com.lucas.springsecurity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> listAll() {
        return userRepository.findAll();
    }
}
