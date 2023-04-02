package com.zerobase.commerce.user.domain.repository;

import com.zerobase.commerce.user.domain.model.SellerEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {

  Optional<SellerEntity> findByIdAndEmail(Long id, String email);

  Optional<SellerEntity> findByEmailAndPasswordAndVerifyIsTrue(String email, String password);

  Optional<SellerEntity> findByEmail(String email);
}
