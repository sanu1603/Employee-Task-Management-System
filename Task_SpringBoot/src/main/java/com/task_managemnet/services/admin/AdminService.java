package com.task_managemnet.services.admin;

import java.util.List;

import com.task_managemnet.dto.CommentDto;
import com.task_managemnet.dto.TaskDto;
import com.task_managemnet.dto.UserDto;

public interface AdminService {

    List<UserDto> getUsers();

    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getAllTasks();

    void deleteTask(Long id);

    TaskDto updateTask(Long id, TaskDto taskDto);

    List<TaskDto> searchTaskByTitle(String title);

    TaskDto getTaskById(Long id);

    CommentDto createComment(Long taskId, String content);

    List<CommentDto>getCommentsByTaskId(Long taskID);

}


