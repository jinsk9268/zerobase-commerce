package com.zerobase.commerce.order.domain.repository;

import com.zerobase.commerce.order.domain.model.ProductEntity;
import java.util.List;

public interface ProductRepositoryCustom {

  List<ProductEntity> searchByName(String name);
}
