package com.lucas.springsecurity.controller;

import com.lucas.springsecurity.dto.UpdateUserDTO;
import com.lucas.springsecurity.entity.User;
import com.lucas.springsecurity.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getALl() {
        return userService.listAll();
    }

    @PutMapping
    public User update(@ModelAttribute UpdateUserDTO user, @RequestHeader("Authorization") String token){
        User newUser = userService.updateUser(user,token.substring(7));
        return newUser;
    }
}
