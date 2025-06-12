package com.task_managemnet.controller.auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task_managemnet.dto.AuthenticationRequest;
import com.task_managemnet.dto.AuthenticationResponse;
import com.task_managemnet.dto.SignupRequest;
import com.task_managemnet.dto.UserDto;
import com.task_managemnet.entities.User;
import com.task_managemnet.repositories.UserRepository;
import com.task_managemnet.services.auth.authService;
import com.task_managemnet.services.jwt.UserService;
import com.task_managemnet.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final authService AuthService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if(AuthService.hasUserWithEmail(signupRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with this email");
        UserDto createdUserDto = AuthService.signupUser(signupRequest);
        if(createdUserDto== null)
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), 
                authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser =userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
       
        return authenticationResponse;
    }

}
