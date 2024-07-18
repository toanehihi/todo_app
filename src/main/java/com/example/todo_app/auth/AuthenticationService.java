package com.example.todo_app.auth;

import com.example.todo_app.common.Role;
import com.example.todo_app.configuration.JwtService;
import com.example.todo_app.entity.User;
import com.example.todo_app.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpHeaders;
import java.io.IOException;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthenticationService(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtService jwtService,AuthenticationManager authenticationManager){
        this.userRepository=userRepository;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
        this.passwordEncoder=passwordEncoder;
    }
    public AuthenticationResponse register(RegisterRequest request){
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).user(user).build();
    }


//    public AuthenticationResponse authenticate(AuthenticationRequest request){
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
//        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
//                .user(user)
//                .build();
//    }
public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );
    var user = userRepository.findUserByEmail(request.getEmail())
            .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
}

    public void refreshToken(HttpServletRequest request, HttpServletResponse response)throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String userEmail;
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken=authHeader.substring(7);
        userEmail=jwtService.extractUsername(refreshToken);
        if(userEmail!=null){
            var user = this.userRepository.findUserByEmail(userEmail).orElseThrow();
            if(jwtService.isTokenValid(refreshToken,user)){
                var accessToken = jwtService.generateToken(user);
                var authResponse= AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .user(user)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}
