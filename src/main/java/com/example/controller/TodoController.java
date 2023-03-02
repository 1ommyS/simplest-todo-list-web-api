package com.example.controller;

import com.example.dto.CompleteTodoDto;
import com.example.dto.TodoCreateDto;
import com.example.dto.TodoDto;
import com.example.exception.JwtTokenExpiredException;
import com.example.model.Todo;
import com.example.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 1ommy
 * @version 18.02.2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoController {
    private final TodoService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<TodoDto> getAllTodos(@RequestParam String token) throws JwtTokenExpiredException {
        return service.getAllTodos(token);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public void createTodo(@RequestBody TodoCreateDto todoCreateDto) throws JwtTokenExpiredException {
        service.createTodo(todoCreateDto);
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public void completeTodo(@RequestBody CompleteTodoDto dto) throws JwtTokenExpiredException {
        service.completeTodo(dto);
    }
}
