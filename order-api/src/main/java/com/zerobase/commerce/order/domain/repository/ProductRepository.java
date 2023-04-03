package com.zerobase.commerce.order.domain.repository;

import com.zerobase.commerce.order.domain.model.ProductEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
  @EntityGraph(attributePaths = {"productItems"}, type = EntityGraphType.LOAD)
  Optional<ProductEntity> findWithProductItemsById(Long id);
}
