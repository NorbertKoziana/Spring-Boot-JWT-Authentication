package com.norbertkoziana.JwtAuth.auth;
import com.norbertkoziana.JwtAuth.auth.config.JwtService;
import com.norbertkoziana.JwtAuth.auth.dto.AuthenticationRequest;
import com.norbertkoziana.JwtAuth.auth.dto.AuthenticationResponse;
import com.norbertkoziana.JwtAuth.auth.dto.RegisterRequest;
import com.norbertkoziana.JwtAuth.user.Role;
import com.norbertkoziana.JwtAuth.user.User;
import com.norbertkoziana.JwtAuth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .isLocked(false)
                .role(Role.User)
                .build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(registerRequest.getEmail());
        return AuthenticationResponse.builder().jwt(jwt).build();
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        //if we get to this point then it means that username and password is correct because the above will throw exception
        String jwt = jwtService.generateToken(authenticationRequest.getEmail());
        return AuthenticationResponse.builder().jwt(jwt).build();
    }
}
