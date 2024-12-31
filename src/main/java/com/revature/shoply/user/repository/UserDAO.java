package com.revature.shoply.user.repository;


import com.revature.shoply.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDAO extends JpaRepository<User, UUID> {
    public User findByEmail(String email);
}