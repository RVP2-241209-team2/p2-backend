package com.revature.shoply.login.repository;

import com.revature.shoply.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoginDAO extends JpaRepository<User, UUID> {
    User findByUsernameAndPassword(String username, String password);
}
