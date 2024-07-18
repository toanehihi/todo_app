package com.example.todo_app.auth;

import com.example.todo_app.entity.User;
import com.example.todo_app.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    public final AuthenticationService authService;
    private final UserRepository userRepository;
    @GetMapping("/admin/getAllUser")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    @PostMapping("/register")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        System.out.println("request = "+request);
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/authenticate")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException {
        authService.refreshToken(request,response);
    }
}
