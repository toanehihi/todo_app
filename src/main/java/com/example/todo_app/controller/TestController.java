package com.example.todo_app.controller;

import com.example.todo_app.service.OauthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final OauthUser oauthUser;
    @GetMapping("/")
    public String test(){
        return "Hello World!";
    }

    @GetMapping("/secured")
    public String secured(){
        return oauthUser.addOauthUser();
    }

}
