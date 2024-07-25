package com.teamsparta.task.todo.service;

import com.teamsparta.task.todo.dto.TodoRequestDTO;
import com.teamsparta.task.todo.dto.TodoResponseDTO;
import com.teamsparta.task.todo.dto.TodoUpdateDTO;
import com.teamsparta.task.todo.model.Todo;
import com.teamsparta.task.todo.repository.TodoRepository;
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
        todo.setCreatedAt(new Date());//현재 날짜를 저장

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
        Todo todo = todoRepository.findById(id)//todo의 아이디로 조회
                .orElseThrow(() -> new RuntimeException("Todo not found"));//Optional인 orElseThrow로 아이디가 없으면 익셉션
        return convertToResponseDTO(todo); // Todo를 TodoResponseDTO로 변환,
    }

//    @Override
//    public List<Todo> getAll() {
//        return todoRepository.findAllByOrderByIdDesc();
//    }
    @Override
    public List<TodoResponseDTO> getAll() {
        List<Todo> todos = todoRepository.findAllByOrderByCreatedAtDesc();//내림차순으로 모든 todo를 가져옴 밑에 에외처리

        if (todos.isEmpty()) {
            throw new RuntimeException("No todos found");
        }//데이터 비엇을경우 예외처리

        List<TodoResponseDTO> todoResponseDTOs = todos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return todoResponseDTOs; // List<Todo>를 List<TodoResponseDTO>로 변환
    }//아무것도 없을때 예외처리 추가하기, 이거 좀더 살펴보기

    @Override
    public TodoResponseDTO update(Long id, TodoUpdateDTO todoUpdateDTO, String password) {
//비번, 아이디, 리퀘스트를 받아 리스폰스를 반환
//    public Todo update(Long id, TodoRequestDTO todoRequestDTO, String password) {
        Todo existingTodo = todoRepository.findById(id)//아이디 찾고 없으면 예외
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!existingTodo.getPassword().equals(password)) {//비번 안맞으면 예외
            throw new RuntimeException("Invalid password");
        }
//dto의 getter, setter 덕분에 사용
        existingTodo.setTitle(todoUpdateDTO.getTitle());//todoRequestDTO의 getTitle()를 호출하여 할 일의 제목을 가져옴
        existingTodo.setContent(todoUpdateDTO.getContent());
        existingTodo.setAssignee(todoUpdateDTO.getAssignee());

//        existingTodo.setPassword(todoUpdateDTO.getPassword());// 비밀번호는 이제 수정 안함


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

    private TodoResponseDTO convertToResponseDTO(Todo todo) {//todo를 TodoResponseDTO로 전환
        TodoResponseDTO responseDTO = new TodoResponseDTO();//새로운 TodoResponseDTO 생성
        // dto의 setter 덕분에 사용
        responseDTO.setId(todo.getId());//아이디 추가함.. 빼먹은게 많다..

        responseDTO.setTitle(todo.getTitle());//todo의 제목을 TodoResponseDTO로 가져옴
        responseDTO.setContent(todo.getContent());
        responseDTO.setAssignee(todo.getAssignee());
        responseDTO.setCreationDate(todo.getCreatedAt());
        return responseDTO;
    }
}
