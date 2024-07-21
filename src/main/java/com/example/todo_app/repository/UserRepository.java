package com.example.todo_app.repository;

import com.example.todo_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}

