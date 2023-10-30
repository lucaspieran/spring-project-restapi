package com.lucas.springsecurity.service;

import com.lucas.springsecurity.config.JwtService;
import com.lucas.springsecurity.dto.UpdateUserDTO;
import com.lucas.springsecurity.entity.User;
import com.lucas.springsecurity.repository.UserRepository;
import lombok.AllArgsConstructor;
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
}
