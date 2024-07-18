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
    {//TodoRequestDTO를 입력받아 todo 생성
        TodoResponseDTO createdTodo = todoService.create(todoRequestDTO);//서비스를 통해 리퀘스트로 생성, 리스폰스 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);//완료하면 201 반환
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> getTodo(@PathVariable Long id) {
        TodoResponseDTO todoResponseDTO = todoService.get(id);
        return ResponseEntity.ok(todoResponseDTO);//완료시 리스폰스 보여줌
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