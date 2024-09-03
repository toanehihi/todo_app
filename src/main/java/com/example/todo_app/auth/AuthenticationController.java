package com.example.todo_app.auth;

import com.example.todo_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/auth")

public class AuthenticationController {
    public final AuthenticationService authService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;




    @PostMapping("/register")

    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        System.out.println("request = "+request);
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/check-email/")
    public boolean existEmail(@RequestParam String email){
        return userRepository.findUserByEmail(email).isPresent();
    }


}
