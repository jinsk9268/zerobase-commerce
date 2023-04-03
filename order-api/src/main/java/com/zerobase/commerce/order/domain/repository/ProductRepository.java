package com.zerobase.commerce.order.domain.repository;

import com.zerobase.commerce.order.domain.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
