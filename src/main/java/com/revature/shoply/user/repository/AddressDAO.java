package com.revature.shoply.user.repository;

import com.revature.shoply.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressDAO extends JpaRepository<Address, UUID> {
    List<Address> findByUserId(UUID userId);
}