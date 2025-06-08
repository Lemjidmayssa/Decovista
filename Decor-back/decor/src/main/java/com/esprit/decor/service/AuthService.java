package com.esprit.decor.service;

import com.esprit.decor.dto.*;
import com.esprit.decor.entity.User;
import com.esprit.decor.repository.UserRepository;
import com.esprit.decor.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder encoder;

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(request.getRole())
                .build();
        userRepo.save(user);
        return new AuthResponse(jwtService.generateToken(user.getUsername(), user.getRole().name()));
    }

    public AuthResponse login(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        return new AuthResponse(jwtService.generateToken(user.getEmail(), user.getRole().name()));
    }
}
