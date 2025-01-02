package com.revature.shoply.user.repository;

import com.revature.shoply.models.Address;
import com.revature.shoply.models.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentMethodDAO extends JpaRepository<PaymentDetails, UUID> {
    List<PaymentDetails> findByUserId(UUID userId);
}