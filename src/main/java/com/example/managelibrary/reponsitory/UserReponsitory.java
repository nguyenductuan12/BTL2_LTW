package com.example.managelibrary.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.managelibrary.entity.User;

@Repository
@EnableJpaRepositories
public interface UserReponsitory extends JpaRepository<User,Integer> {
    User getUserByEmail(String email);
    User findUserByEmail(String email);
    @Query(
            value = "select * from user",
            nativeQuery = true)
    List<User> getAllUser();
}
