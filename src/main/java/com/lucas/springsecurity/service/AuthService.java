package com.lucas.springsecurity.service;

import com.lucas.springsecurity.controller.models.AuthResponse;
import com.lucas.springsecurity.controller.models.AuthenticationRequest;
import com.lucas.springsecurity.controller.models.RegisterRequest;

public interface AuthService {

    AuthResponse register (RegisterRequest request);

    AuthResponse authenticate (AuthenticationRequest request);
}
