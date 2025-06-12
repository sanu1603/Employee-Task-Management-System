package com.task_managemnet.dto;

import com.task_managemnet.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;
    
    private Long userId;

    private UserRole userRole;
}
