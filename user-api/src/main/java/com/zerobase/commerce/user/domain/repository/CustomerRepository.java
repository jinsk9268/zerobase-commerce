package com.zerobase.commerce.user.domain.repository;

import com.zerobase.commerce.user.domain.model.CustomerEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

  Optional<CustomerEntity> findByEmail(String email);
}
