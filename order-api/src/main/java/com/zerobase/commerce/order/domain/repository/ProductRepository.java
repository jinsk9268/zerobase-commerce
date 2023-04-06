package com.zerobase.commerce.order.domain.repository;

import com.zerobase.commerce.order.domain.model.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
    extends JpaRepository<ProductEntity, Long>, ProductRepositoryCustom {

  @EntityGraph(attributePaths = {"productItems"}, type = EntityGraphType.LOAD)
  Optional<ProductEntity> findWithProductItemsById(Long id);

  @EntityGraph(attributePaths = {"productItems"}, type = EntityGraphType.LOAD)
  Optional<ProductEntity> findBySellerIdAndId(Long sellerId, Long id);

  @EntityGraph(attributePaths = {"productItems"}, type = EntityGraphType.LOAD)
  List<ProductEntity> findAllByIdIn(List<Long> ids);
}
