package com.zerobase.commerce.order.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.commerce.order.domain.model.ProductEntity;
import com.zerobase.commerce.order.domain.model.QProductEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<ProductEntity> searchByName(String name) {
    String search = "%" + name + "%";

    QProductEntity product = QProductEntity.productEntity;

    return jpaQueryFactory.selectFrom(product)
        .where(product.name.like(search))
        .fetch();
  }
}
