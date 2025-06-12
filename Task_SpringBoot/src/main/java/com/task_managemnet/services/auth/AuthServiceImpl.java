package com.task_managemnet.services.auth;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.task_managemnet.dto.SignupRequest;
import com.task_managemnet.dto.UserDto;
import com.task_managemnet.entities.User;
import com.task_managemnet.enums.UserRole;
import com.task_managemnet.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements authService{

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
        Optional<User> optionalUser = userRepository.findByUserRole(UserRole.ADMIN);
        if(optionalUser.isEmpty()){
            User user =new User();
            user.setEmail("sanu16@gmail.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("sanika"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("Admin Account Created Successfully");
        } else{
            System.out.println("Admin Account Already Exist!");
        }
    }

    @Override
    public UserDto signupUser(SignupRequest signupRequest) {
       User user =new User();
       user.setEmail(signupRequest.getEmail());
       user.setName(signupRequest.getName());
       user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
       user.setUserRole(UserRole.EMPLOYEE);
       User createdUser= userRepository.save(user);
       return createdUser.getUserDto();

    }

    @Override
    public boolean hasUserWithEmail(String email) {
       return userRepository.findFirstByEmail(email).isPresent();
    }
}
