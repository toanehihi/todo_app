package com.example.todo_app.repository;

import com.example.todo_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findUserByEmail(String email);

//    @Transactional
//    @Modifying
//    @Query("UPDATE User a " +
//            "SET a.enabled = TRUE WHERE a.email = ?1")
//    int enableUser(String email);
}
