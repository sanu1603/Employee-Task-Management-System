package com.task_managemnet.services.employee;

import java.util.List;

import com.task_managemnet.dto.CommentDto;
import com.task_managemnet.dto.TaskDto;

public interface EmployeeService {

    List<TaskDto> getTasksByUserId();

    TaskDto updateTask(Long id, String status);

     TaskDto getTaskById(Long id);

    CommentDto createComment(Long taskId, String content);

    List<CommentDto>getCommentsByTaskId(Long taskID);

}
