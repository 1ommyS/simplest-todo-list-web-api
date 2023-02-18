package com.example.controller;

import com.example.dto.TodoCreateDto;
import com.example.exception.JwtTokenExpired;
import com.example.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void createTodo(@RequestBody TodoCreateDto todoCreateDto) throws JwtTokenExpired {
        service.createTodo(todoCreateDto);
    }
}