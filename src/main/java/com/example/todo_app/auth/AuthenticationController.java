package com.example.todo_app.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    public final AuthenticationService authService;
    @GetMapping("/me")
    public String me(){
        return "Hello World!";
    }

    @PostMapping("/register")

    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        System.out.println("request = "+request);
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
