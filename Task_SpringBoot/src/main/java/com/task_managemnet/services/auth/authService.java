package com.task_managemnet.services.auth;

import com.task_managemnet.dto.SignupRequest;
import com.task_managemnet.dto.UserDto;

public interface authService {

    UserDto signupUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
    
}
