package com.example.todo_app.service.Impl;

import com.example.todo_app.common.Role;
import com.example.todo_app.entity.User;
import com.example.todo_app.repository.UserRepository;
import com.example.todo_app.service.OauthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class OauthUserImpl implements OauthUser {
    private final UserRepository userRepository;
    @Override
    public String addOauthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof OAuth2AuthenticationToken oauthToken){
            OAuth2User oauthUser = oauthToken.getPrincipal();
            if(oauthUser !=null){
                Map<String, Object> attributes = oauthUser.getAttributes();
                Optional<User> optionalUser = userRepository.findUserByEmail((String) attributes.get("email"));
                if(optionalUser.isEmpty()){
                    User user = new User();
                    user.setEmail((String) attributes.get("email"));
                    user.setFirstname((String) attributes.get("name"));
                    user.setRole(Role.USER);
                    userRepository.save(user);
                    System.out.println(user);
                    return "New Oauth2 User Added " + user.getFirstname()+" " + user.getLastname();
                }
            }
        }


        return "User already exists";
    }
}
