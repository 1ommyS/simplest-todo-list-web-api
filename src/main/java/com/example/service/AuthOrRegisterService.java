package com.example.service;

import com.example.dto.AuthOrRegisterResponseDto;
import com.example.dto.AuthRequestDto;
import com.example.dto.RegisterRequestDto;
import com.example.model.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthOrRegisterService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthOrRegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        User user = User.builder()
                .name(registerRequestDto.getName())
                .login(registerRequestDto.getLogin())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .email(registerRequestDto.getEmail())
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthOrRegisterResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthOrRegisterResponseDto authenticate(AuthRequestDto authRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getLogin(),
                        authRequestDto.getPassword()
                )
        );

        User user = repository.findByLogin(authRequestDto.getLogin())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return AuthOrRegisterResponseDto.builder()
                .token(jwtToken)
                .build();
    }
}
