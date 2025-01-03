package com.revature.shoply.Repositories;

import com.revature.shoply.models.Product;
import com.revature.shoply.models.ProductTag;
import com.revature.shoply.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductTagDAO extends JpaRepository<ProductTag, UUID> {
    Optional<ProductTag> findByTag(Tag tag);
}
