package com.teamsparta.task.service;

import com.teamsparta.task.dto.TodoRequestDTO;
import com.teamsparta.task.dto.TodoResponseDTO;
import com.teamsparta.task.dto.TodoUpdateDTO;


import java.util.List;

public interface TodoService {
    //todo로 했던것들을 TodoResponseDTO이렇게 dto랑 연결
    TodoResponseDTO create(TodoRequestDTO todoRequestDTO);//TodoRequestDTO이거랑 연결하려면 TodoRequestDTO todoRequestDTO 해야함
    // TodoRequestDTO와 연결되어 생성후 TodoResponseDTO를 반환
    TodoResponseDTO get(Long id);// 누르면 TodoResponseDTO를 반환
    List<TodoResponseDTO> getAll();//누르면 TodoResponseDTO를 반환하지만, list, 즉 목록을 반환
    TodoResponseDTO update(Long id, TodoUpdateDTO todoUpdateDTO, String password);//Request를 UpdateDto로 수정
    void delete(Long id,String password);// dto와 연결되지도, 뭔가를 반환하지도 않으니 void
}
