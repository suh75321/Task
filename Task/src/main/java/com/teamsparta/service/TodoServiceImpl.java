package com.teamsparta.service;

import com.teamsparta.dto.TodoRequestDTO;
import com.teamsparta.dto.TodoResponseDTO;
import com.teamsparta.model.Todo;
import com.teamsparta.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;


import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public TodoResponseDTO  create(TodoRequestDTO todoRequestDTO) {//리퀘스트를 받아 스시폰스를 반환
        Todo todo = new Todo();//새로운 todo를 생성
        todo.setTitle(todoRequestDTO.getTitle());//setter과 getter로 todoRequestDTO에서 제목 정보를 가져와 todo 객체의 title에 설정
        todo.setContent(todoRequestDTO.getContent());//마찬가지의 방법으로 내용을 가져옴
        todo.setAssignee(todoRequestDTO.getAssignee());
        todo.setPassword(todoRequestDTO.getPassword());
        todo.setCreationDate(new Date());//현재 날짜를 저장

        Todo createdTodo = todoRepository.save(todo);//todoRepository를 이용해 todo 객체를 데이터베이스에 저장하고, 저장된 todo를 createdTodo에 저장
        return convertToResponseDTO(createdTodo); // Todo를 TodoResponseDTO로 변환
//        return todoRepository.save(todo);
    }

//    @Override dto 연결전
//    public Todo get(Long id) {
//        return todoRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Todo not found"));
//    }
    @Override
    public TodoResponseDTO get(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        return convertToResponseDTO(todo); // Todo를 TodoResponseDTO로 변환
    }

//    @Override
//    public List<Todo> getAll() {
//        return todoRepository.findAllByOrderByIdDesc();
//    }
    @Override
    public List<TodoResponseDTO> getAll() {
        List<Todo> todos = todoRepository.findAllByOrderByIdDesc();
        List<TodoResponseDTO> todoResponseDTOs = todos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return todoResponseDTOs; // List<Todo>를 List<TodoResponseDTO>로 변환
    }

    @Override
    public TodoResponseDTO update(Long id, TodoRequestDTO todoRequestDTO, String password) {

//    public Todo update(Long id, TodoRequestDTO todoRequestDTO, String password) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!existingTodo.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        existingTodo.setTitle(todoRequestDTO.getTitle());//dto의 getter, setter 덕분에 사용
        existingTodo.setContent(todoRequestDTO.getContent());
        existingTodo.setAssignee(todoRequestDTO.getAssignee());

        Todo updatedTodo = todoRepository.save(existingTodo);
        return convertToResponseDTO(updatedTodo); // Todo를 TodoResponseDTO로 변환
        //        return todoRepository.save(existingTodo);

    }


    @Override
    public void delete(Long id, String password) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        todoRepository.delete(todo);
    }

    private TodoResponseDTO convertToResponseDTO(Todo todo) {
        TodoResponseDTO responseDTO = new TodoResponseDTO();
        responseDTO.setTitle(todo.getTitle());// dto의 setter 덕븐에 사용
        responseDTO.setContent(todo.getContent());
        responseDTO.setAssignee(todo.getAssignee());
        responseDTO.setCreationDate(todo.getCreationDate());
        return responseDTO;
    }
}
