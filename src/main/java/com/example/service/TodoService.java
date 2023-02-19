package com.example.service;

import com.example.dto.TodoCreateDto;
import com.example.dto.UserDto;
import com.example.exception.JwtTokenExpiredException;
import com.example.model.Todo;
import com.example.model.User;
import com.example.repository.TodoRepository;
import com.example.repository.UserRepository;
import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author 1ommy
 * @version 18.02.2023
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository repository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final Gson gson;
    private final JwtService jwtService;

    public void createTodo(TodoCreateDto dto) throws JwtTokenExpiredException {
        var token = dto.getToken();

        if (jwtService.isTokenExpired(token)) {
            throw new JwtTokenExpiredException("Your token is expired");
        }

        String userPayloadJson = jwtService.extractUsername(token);
        UserDto userDto = gson.fromJson(userPayloadJson, UserDto.class);

        User user = userRepository.findByLogin(userDto.getLogin()).orElseThrow(() -> {
            throw new EntityNotFoundException("This user isn't exist");
        });

        Todo todo = Todo.builder()
                .text(dto.getText())
                .user(user)
                .isDone(false)
                .build();

        repository.save(todo);
    }
}
