package com.example.service;

import com.example.dto.AuthOrRegisterResponseDto;
import com.example.dto.AuthRequestDto;
import com.example.dto.RegisterRequestDto;
import com.example.dto.UserDto;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthOrRegisterService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final Gson gson;
    private final ModelMapper mapper;
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

        UserDto userDto = mapper.map(user, UserDto.class);
        String userDtoJson = gson.toJson(userDto);

        var jwtToken = jwtService.generateToken(userDtoJson);

        return AuthOrRegisterResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthOrRegisterResponseDto authenticate(AuthRequestDto authRequestDto) {
        /*authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getLogin(),
                        authRequestDto.getPassword()
                )
                );*/
        return AuthOrRegisterResponseDto.builder().token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c").build();



        /*User user = repository.findByLogin(authRequestDto.getLogin())
                .orElseThrow();

        UserDto userDto = mapper.map(user, UserDto.class);
        String userDtoJson = gson.toJson(userDto);

        String jwtToken = jwtService.generateToken(userDtoJson);

        return AuthOrRegisterResponseDto.builder()
                .token(jwtToken)
                .build();*/
    }


}
