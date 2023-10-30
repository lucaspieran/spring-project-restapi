package com.lucas.springsecurity.controller;

import com.lucas.springsecurity.dto.UpdateRoleDTO;
import com.lucas.springsecurity.dto.UpdateUserDTO;
import com.lucas.springsecurity.entity.User;
import com.lucas.springsecurity.service.UserService;
import com.lucas.springsecurity.util.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
       return userService.updateUser(user,token.substring(7));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateRole(@RequestBody UpdateRoleDTO body, @PathVariable Long id) throws Exception {
        try {
            userService.updateUserRole(body, id);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Updated");
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (CustomException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, e.getStatus());
        }
    }
}
