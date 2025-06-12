package com.task_managemnet.services.admin;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.task_managemnet.dto.CommentDto;
import com.task_managemnet.dto.TaskDto;
import com.task_managemnet.dto.UserDto;
import com.task_managemnet.entities.Comment;
import com.task_managemnet.entities.Task;
import com.task_managemnet.entities.User;
import com.task_managemnet.enums.TaskStatus;
import com.task_managemnet.enums.UserRole;
import com.task_managemnet.repositories.CommentRepository;
import com.task_managemnet.repositories.TaskRepository;
import com.task_managemnet.repositories.UserRepository;
import com.task_managemnet.utils.JwtUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final CommentRepository commentRepository;

    private final JwtUtil jwtUtil;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUserRole()== UserRole.EMPLOYEE)
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
       Optional<User> optionalUser = userRepository.findById(taskDto.getEmployeeId());
       if (optionalUser.isPresent()) {
            Task task =new Task();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setDueDate(taskDto.getDueDate());
            task.setTaskstatus(TaskStatus.INPROGRESS);
            task.setUser(optionalUser.get());
            return taskRepository.save(task).getTaskDto();
    }
    return null;
}

    @Override
    public List<TaskDto> getAllTasks() {
       return taskRepository.findAll()
       .stream()
       .sorted(Comparator.comparing(Task::getDueDate).reversed())
       .map(Task::getTaskDto)
       .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
       taskRepository.deleteById(id);
    }


    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(taskDto.getEmployeeId());
        if(optionalTask.isPresent() && optionalUser.isPresent()){
            Task existingTask = optionalTask.get();
            existingTask.setTitle(taskDto.getTitle());
            existingTask.setDescription(taskDto.getDescription());
            existingTask.setDueDate(taskDto.getDueDate());
            existingTask.setPriority(taskDto.getPriority());
            //existingTask.setTaskstatus(mapStringToTaskStatus(String.valueOf(taskDto.getTaskstatus())));
            existingTask.setUser(optionalUser.get());
            return taskRepository.save(existingTask).getTaskDto();
        }
        return null;
    }

    @Override
    public List<TaskDto> searchTaskByTitle(String title) {
       return taskRepository.findAllByTitleContaining(title)
            .stream()
            .sorted(Comparator.comparing(Task::getDueDate).reversed())
            .map(Task::getTaskDto)
            .collect(Collectors.toList());
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
}
