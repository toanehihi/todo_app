package com.example.todo_app.configuration;

import com.example.todo_app.common.Role;
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
    public CorsConfiguration corsConfiguration(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        return corsConfiguration;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(cors->cors.configurationSource(request -> corsConfiguration()))
                .csrf(AbstractHttpConfigurer::disable) //disable CSRF protection(protection against CSRF attack)
                //configure authentication and authorization of HTTP requests.
                .authorizeHttpRequests(req->
                        req.requestMatchers("/api/**").permitAll()
//                        req.requestMatchers("/api/v1/auth/**").permitAll()
//                                .requestMatchers("/api/v1/user/**").hasAuthority(Role.USER.name())
                                .anyRequest().authenticated())
                .sessionManagement(sess-> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //sessions do not store state
                .authenticationProvider(authenticationProvider) //provide an authenticationProvider to authenticate the user.
                .addFilterBefore(jawAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



}
