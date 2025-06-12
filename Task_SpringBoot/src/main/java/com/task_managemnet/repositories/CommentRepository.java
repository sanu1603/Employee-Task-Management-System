package com.task_managemnet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task_managemnet.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List <Comment> findAllByTaskId(Long taskId);
}
