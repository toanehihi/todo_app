package com.example.todo_app.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jawAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable) //disable CSRF protection(protection against CSRF attack)
                //configure authentication and authorization of HTTP requests.
                .authorizeHttpRequests(req->
                        req.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/auth/me").hasAuthority("USER")
                                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated())
                .sessionManagement(sess-> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //sessions do not store state
                .authenticationProvider(authenticationProvider) //provide an authenticationProvider to authenticate the user.
                .addFilterBefore(jawAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



}
