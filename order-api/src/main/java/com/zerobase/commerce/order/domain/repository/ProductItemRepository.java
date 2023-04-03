package com.zerobase.commerce.order.domain.repository;

import com.zerobase.commerce.order.domain.model.ProductItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<ProductItemEntity, Long> {
}
