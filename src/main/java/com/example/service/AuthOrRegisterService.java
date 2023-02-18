package com.example.service;

import com.example.dto.AuthOrRegisterResponseDto;
import com.example.dto.AuthRequestDto;
import com.example.dto.RegisterRequestDto;
import com.example.dto.UserDto;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthOrRegisterService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final Gson gson;
    private final ModelMapper mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthOrRegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        log.info("AuthOrRegisterService.register - in: get dto");
        User user = User.builder()
                .name(registerRequestDto.getName())
                .login(registerRequestDto.getLogin())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .email(registerRequestDto.getEmail())
                .build();

        log.info("Build user with name: {}, login:{}, email:{}", registerRequestDto.getName(), registerRequestDto.getLogin(), registerRequestDto.getEmail());
        repository.save(user);
        log.info("User is saved");

        UserDto userDto = mapper.map(user, UserDto.class);
        String userDtoJson = gson.toJson(userDto);

        log.info("User was converted to JSON");
        var jwtToken = jwtService.generateToken(userDtoJson);
        log.info("Token was generated: {}", jwtToken);

        return AuthOrRegisterResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthOrRegisterResponseDto authenticate(AuthRequestDto authRequestDto) {
        log.info("Preparing user to authentication");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getLogin(),
                        authRequestDto.getPassword()
                )
        );
        log.info("User was authenticated");

        User user = repository.findByLogin(authRequestDto.getLogin())
                .orElseThrow();
        log.info("User from db: {}", user.toString());

        UserDto userDto = mapper.map(user, UserDto.class);
        String userDtoJson = gson.toJson(userDto);
        log.info("UserDtoJson was generated");

        String jwtToken = jwtService.generateToken(userDtoJson);
        log.info("Generate jwt token");
        
        return AuthOrRegisterResponseDto.builder()
                .token(jwtToken)
                .build();
    }


}
