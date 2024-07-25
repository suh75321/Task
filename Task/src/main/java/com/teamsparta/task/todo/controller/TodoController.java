package com.teamsparta.task.todo.controller;

import com.teamsparta.task.todo.dto.TodoRequestDTO;
import com.teamsparta.task.todo.dto.TodoResponseDTO;
import com.teamsparta.task.todo.dto.TodoUpdateDTO;
import com.teamsparta.task.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "새로운 할일 생성", description = "새 할일을 만드시오")
    //summary는 제목, description는 내용
    @PostMapping
    public ResponseEntity<TodoResponseDTO> createTodo(@RequestBody TodoRequestDTO todoRequestDTO )
    {//TodoRequestDTO를 입력받아 todo 생성
        TodoResponseDTO createdTodo = todoService.create(todoRequestDTO);//서비스를 통해 리퀘스트로 생성, 리스폰스 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);//완료하면 201 반환
    }

    @Operation(summary = "개별 조회", description = "조회할 ID를 입력하시오")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo found"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> getTodo(@PathVariable Long id) {
        TodoResponseDTO todoResponseDTO = todoService.get(id);
        return ResponseEntity.ok(todoResponseDTO);//완료시 리스폰스 보여줌
    }


    @Operation(summary = "전체 조회", description = "그냥 보시오")
    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> getAllTodos() {
        List<TodoResponseDTO> todoResponseDTOs = todoService.getAll();
        return ResponseEntity.ok(todoResponseDTOs);
    }

    //Request를 UpdateDto로 수정
    @Operation(summary = "할일 수정", description = "ID를 입력하고 제목, 내용, 담당자 중 아무거나 수정하세요")
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> updateTodo(@PathVariable Long id, @RequestBody TodoUpdateDTO todoUpdateDTO, @RequestParam String password) {
        TodoResponseDTO updatedTodoResponseDTO = todoService.update(id, todoUpdateDTO, password);
        return ResponseEntity.ok(updatedTodoResponseDTO);
    }

    @Operation(summary = "할일 삭제", description = "삭제할 할일의 ID를 입력하시오")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id, @RequestParam String password) {
        todoService.delete(id, password);
        return ResponseEntity.noContent().build();
    }
}