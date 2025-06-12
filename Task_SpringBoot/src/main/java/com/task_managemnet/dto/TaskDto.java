package com.task_managemnet.dto;

import java.util.Date;

import com.task_managemnet.enums.TaskStatus;

import lombok.Data;

@Data
public class TaskDto {

    private Long id;

    private String title;

    private String description;

    private Date dueDate;

    private String priority;

    private TaskStatus taskstatus;

    private Long employeeId;
    
    private String employeeName;
}
