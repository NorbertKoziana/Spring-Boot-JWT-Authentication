package com.norbertkoziana.JwtAuth.auth;

import com.norbertkoziana.JwtAuth.auth.dto.AuthenticationRequest;
import com.norbertkoziana.JwtAuth.auth.dto.AuthenticationResponse;
import com.norbertkoziana.JwtAuth.auth.dto.RegisterRequest;
public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
