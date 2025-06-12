package com.task_managemnet.services.employee;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.task_managemnet.dto.CommentDto;
import com.task_managemnet.dto.TaskDto;
import com.task_managemnet.entities.Comment;
import com.task_managemnet.entities.Task;
import com.task_managemnet.entities.User;
import com.task_managemnet.enums.TaskStatus;
import com.task_managemnet.repositories.CommentRepository;
import com.task_managemnet.repositories.TaskRepository;
import com.task_managemnet.utils.JwtUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements  EmployeeService{

    private final TaskRepository taskRepository;

    private final JwtUtil jwtUtil; 

    private final CommentRepository commentRepository;

    @Override
    public List<TaskDto> getTasksByUserId() {
        User user = jwtUtil.getLoggedInUser();
        if(user != null) {
            return taskRepository.findAllByUserId(user.getId())
            .stream()
            .sorted(Comparator.comparing(Task::getDueDate).reversed())
            .map(Task::getTaskDto)
            .collect(Collectors.toList());
        }
      throw new EntityNotFoundException("User not found");
    }

    @Override
    public TaskDto updateTask(Long id, String status) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task existingTask = optionalTask.get();
            existingTask.setTaskstatus(mapStringToTaskStatus(status));
            return taskRepository.save(existingTask).getTaskDto();
        }
        throw new EntityNotFoundException("Task not found");
    }

     @Override
    public TaskDto getTaskById(Long id) {
       Optional<Task> optionalTask = taskRepository.findById(id);
       return optionalTask.map(Task::getTaskDto).orElse(null);
    }

    
  @Override
    public CommentDto createComment(Long taskId, String content) {
       Optional<Task> optionalTask = taskRepository.findById(taskId);
       User user = jwtUtil.getLoggedInUser();
       if((optionalTask.isPresent()) && user != null){
        Comment comment = new Comment();   
        comment.setCreatedAt(new Date()); 
        comment.setContent(content);
        comment.setTask(optionalTask.get());
        comment.setUser(user);
        return commentRepository.save(comment).getCommentDto();
    }
    throw new EntityNotFoundException("User or Task not found");
   }

  @Override
  public List<CommentDto> getCommentsByTaskId(Long taskId) {
   return commentRepository.findAllByTaskId(taskId).stream().map(Comment::getCommentDto).collect(Collectors.toList());

  }

     private TaskStatus mapStringToTaskStatus(String status){
        return switch(status){
            case"COMPLETED" -> TaskStatus.COMPLETED;
            default -> TaskStatus.INPROGRESS;

        };
    }
}
