package com.lucas.springsecurity.service;

import com.lucas.springsecurity.config.JwtService;
import com.lucas.springsecurity.controller.models.AuthResponse;
import com.lucas.springsecurity.controller.models.AuthenticationRequest;
import com.lucas.springsecurity.controller.models.RegisterRequest;
import com.lucas.springsecurity.entity.Role;
import com.lucas.springsecurity.entity.User;
import com.lucas.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements  AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FileUpload fileUpload;

    @Override
    public AuthResponse register(RegisterRequest request) {
        String img = fileUpload.upload(request.getFile());
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .profileImg(img)
                .build();
        userRepository.save(user);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getId());
        var jwt = jwtService.generateToken(extraClaims, user);
        return AuthResponse.builder().token(jwt).build();
    }

    @Override
    public AuthResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getId());
        var jwtToken = jwtService.generateToken(extraClaims, user);

        return AuthResponse.builder().token(jwtToken).build();
    }
}
