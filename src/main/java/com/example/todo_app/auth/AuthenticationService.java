package com.example.todo_app.auth;

import com.example.todo_app.common.Role;
import com.example.todo_app.configuration.JwtService;
import com.example.todo_app.entity.User;
import com.example.todo_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        userRepository.findUserByEmail(request.getEmail()).ifPresent(user -> {
            throw new RuntimeException("User with email "+request.getEmail() + "already exists");
        });

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).user(user).build();
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
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .user(user)
//                .build();
//    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        }
        catch(Exception e){
//            e.printStackTrace();

                throw new AuthenticationCredentialsNotFoundException("Invalid email or password");
            }
        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(user)
                .build();

    }
}
