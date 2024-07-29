package com.teamsparta.task.todo.controller;

import com.teamsparta.task.todo.dto.*;
import com.teamsparta.task.todo.model.Todo;
import com.teamsparta.task.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

        @Operation(summary = "일정 생성")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Created the todo.")
        })
        @PostMapping
        public TodoResponseDTO createTodo(@RequestBody @Valid TodoRequestDTO request) {
            Todo todo = todoService.createTodo(
                    request.title(),
                    request.content(),
                    request.username(),
                    request.password(),
                    LocalDateTime.now()
            );

            return TodoResponseDTO.of(todo);
        }

        @Operation(summary = "일정 목록 조회")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Fetched the todo.")
        })
        @GetMapping
        public List<SimplifiedTodoResponseDTO> getTodos() {
            return todoService.getTodos()
                    .stream()
                    .map(todo -> new SimplifiedTodoResponseDTO(todo.getId(), todo.getTitle(), todo.getCreatedAt()))
                    .toList();
        }

        @Operation(summary = "일정 단일 조회")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Fetched the todo."),
                @ApiResponse(responseCode = "400", description = "Todo not found."),
        })
        @GetMapping("/{id}")
        public TodoResponseDTO getTodo(@PathVariable("id") Long id) {
            Todo todo = todoService.getTodoById(id);
            return TodoResponseDTO.of(todo);
        }

        @Operation(summary = "일정 수정")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Updated the todo."),
                @ApiResponse(responseCode = "400", description = "Todo not found."),
                @ApiResponse(responseCode = "401", description = "Password not matched.")
        })
        @PatchMapping("/{id}")
        public TodoResponseDTO updateTodo(
                @PathVariable("id") Long id,
                @RequestBody @Valid TodoUpdateDTO request
        ) {
            Todo todo = todoService.updateTodo(
                    id,
                    request.title(),
                    request.content(),
                    request.username(),
                    request.password()
            );
            return TodoResponseDTO.of(todo);
        }

        @Operation(summary = "일정 삭제")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Deleted the todo."),
                @ApiResponse(responseCode = "400", description = "Todo not found."),
                @ApiResponse(responseCode = "401", description = "Password not matched.")
        })
        @DeleteMapping("/{id}")
        public void deleteTodo(
                @PathVariable("id") Long id,
                @RequestBody @Valid TodoDeleteRequestDTO request
        ) {
            todoService.deleteTodo(id, request.password());
        }
    }