package com.revature.shoply.Repositories;

import com.revature.shoply.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagDAO extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByName(String name);
}
