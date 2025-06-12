package com.task_managemnet.dto;

import com.task_managemnet.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private UserRole userRole;
}
