package com.lucas.springsecurity.service;

import com.lucas.springsecurity.config.JwtService;
import com.lucas.springsecurity.dto.UpdateRoleDTO;
import com.lucas.springsecurity.dto.UpdateUserDTO;
import com.lucas.springsecurity.entity.User;
import com.lucas.springsecurity.repository.UserRepository;
import com.lucas.springsecurity.util.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FileUpload fileUpload;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User updateUser(UpdateUserDTO newUser, String token) {
        Map<String, Object> claimsMap = jwtService.getAllClaims(token);
        Object emailObject = claimsMap.get("sub");
        String email = emailObject.toString();

        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (newUser.getLastName() != null) {
                user.setLastName(newUser.getLastName().orElse(null));
            }

            if (newUser.getFirstName() != null) {
                user.setFirstName(newUser.getFirstName().orElse(null));
            }

            if(newUser.getFile() != null) {
                String img = fileUpload.upload(newUser.getFile().orElse(null));
                user.setProfileImg(img);
            }
            userRepository.save(user);

            System.out.println("Usuario actualizado: " + user);
            return user;
        } else {
            System.out.println("Usuario no encontrado para el correo electrónico: " + email);
            return null;
        }
    }

    public User updateUserRole(UpdateRoleDTO newUser, Long id) throws Exception  {
       Optional<User> user = userRepository.findById(id);
       if(user.isPresent()) {
           User updatedUser = user.get();
           if (newUser.getRole() != null) {
               updatedUser.setRole(newUser.getRole());
               return userRepository.save(updatedUser);
           }
       }
           throw new CustomException("User not found with id: " + id, HttpStatus.NOT_FOUND);
    }
}
