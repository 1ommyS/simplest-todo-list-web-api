package com.example.service;

import com.example.dto.CompleteTodoDto;
import com.example.dto.TodoCreateDto;
import com.example.dto.TodoDto;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        User user = userRepository.findByLogin(userDto.getLogin()).orElseThrow(EntityNotFoundException::new);

        Todo todo = Todo.builder()
                .text(dto.getText())
                .user(user)
                .isDone(false)
                .build();

        repository.save(todo);
    }

    public void completeTodo(CompleteTodoDto dto) throws JwtTokenExpiredException {
        var token = dto.getToken();

        if (jwtService.isTokenExpired(token)) {
            throw new JwtTokenExpiredException("Your token is expired");
        }

        repository.completeTodo(dto.getId());
    }

    public List<TodoDto> getAllTodos(String token) throws JwtTokenExpiredException {

        if (jwtService.isTokenExpired(token)) {
            throw new JwtTokenExpiredException("Your token is expired");
        }

        String userPayloadJson = jwtService.extractUsername(token);
        UserDto userDto = gson.fromJson(userPayloadJson, UserDto.class);

        var user = userRepository.findById(userDto.getId()).orElseThrow(EntityNotFoundException::new);

        return repository
                .findAllByUser(user)
                .stream()
                .map(todo -> mapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }
}
