package com.teamsparta.task.todo.service;

import com.teamsparta.task.exception.TodoNotFoundException;
import com.teamsparta.task.exception.TodoPasswordNotMatchedException;
import com.teamsparta.task.todo.model.Todo;
import com.teamsparta.task.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Todo createTodo(
            String title,
            String content,
            String username,
            String password,
            LocalDateTime createdAt
    ) {
        Todo todo = Todo.builder()
                .title(title)
                .content(content)
                .username(username)
                .password(password)
                .createdAt(createdAt)
                .build();

        return todoRepository.save(todo);
    }
    //새로운 투두 항목을 생성합니다. 전달받은 인자를 사용하여 Todo 객체를 생성한 후,
    // 이를 데이터베이스에 저장하고 저장된 객체를 반환합니다.

    public List<Todo> getTodos() {
        return todoRepository.findAllByOrderByCreatedAtDesc();
    }
//모든 투두 항목을 조회하여 생성일자 내림차순으로 정렬된 리스트를 반환
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
    }
//주어진 ID를 사용하여 특정 투두 항목을 조회합니다. 해당 ID가 존재하지 않으면
// TodoNotFoundException을 던집니다.
    public Todo updateTodo(
            Long id,
            String title,
            String content,
            String username,
            String password
    ) {
        Todo todo = getTodoById(id);

        if (!todo.getPassword().equals(password)) {
            throw new TodoPasswordNotMatchedException();
        }

        todo.update(title, content, username);

        return todoRepository.save(todo);
    }
//주어진 ID를 사용하여 특정 투두 항목을 업데이트합니다. 먼저 투두 항목을 조회한 후,
// 비밀번호가 일치하지 않으면 TodoPasswordNotMatchedException을 던집니다.
// 비밀번호가 일치하면 제목, 내용, 사용자명을 업데이트하고 변경된 투두 항목을 저장합니다.
    public void deleteTodo(
            Long id,
            String password
    ) {

        Todo todo = getTodoById(id);

        if (!todo.getPassword().equals(password)) {
            throw new TodoPasswordNotMatchedException();
        }

        todoRepository.delete(todo);
    }//주어진 ID를 사용하여 특정 투두 항목을 삭제합니다. 먼저 투두 항목을 조회한 후,
    // 비밀번호가 일치하지 않으면 TodoPasswordNotMatchedException을 던집니다.
    // 비밀번호가 일치하면 해당 투두 항목을 삭제합니다.
}
