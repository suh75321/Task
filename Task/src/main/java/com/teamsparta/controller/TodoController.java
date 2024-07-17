package com.teamsparta.controller;

import com.teamsparta.dto.TodoRequestDTO;
import com.teamsparta.dto.TodoResponseDTO;
import com.teamsparta.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDTO> createTodo(@RequestBody TodoRequestDTO todoRequestDTO )
    {
        TodoResponseDTO createdTodo = todoService.create(todoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> getTodo(@PathVariable Long id) {
        TodoResponseDTO todoResponseDTO = todoService.get(id);
        return ResponseEntity.ok(todoResponseDTO);
    }


    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> getAllTodos() {
        List<TodoResponseDTO> todoResponseDTOs = todoService.getAll();
        return ResponseEntity.ok(todoResponseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> updateTodo(@PathVariable Long id, @RequestBody TodoRequestDTO todoRequestDTO, @RequestParam String password) {
        TodoResponseDTO updatedTodoResponseDTO = todoService.update(id, todoRequestDTO, password);
        return ResponseEntity.ok(updatedTodoResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id, @RequestParam String password) {
        todoService.delete(id, password);
        return ResponseEntity.noContent().build();
    }
}