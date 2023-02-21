package com.example.repository;

import com.example.dto.TodoDto;
import com.example.model.Todo;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    @Modifying
    @Query(value = "UPDATE Todo t SET t.isDone = true WHERE t.id = ?1")
    @Transactional
    void completeTodo(int id);

    List<Todo> findAllByUser(User user);
}

