package com.zerobase.commerce.order.service;

import static com.zerobase.commerce.order.exception.ErrorCode.NOT_FOUND_PRODUCT;
import static com.zerobase.commerce.order.exception.ErrorCode.SAME_ITEM_NAME;

import com.zerobase.commerce.order.domain.model.ProductEntity;
import com.zerobase.commerce.order.domain.model.ProductItemEntity;
import com.zerobase.commerce.order.domain.product.AddProductItemForm;
import com.zerobase.commerce.order.domain.repository.ProductRepository;
import com.zerobase.commerce.order.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductItemService {

  private final ProductRepository productRepository;

  @Transactional
  public ProductEntity addProductItem(Long sellerId, AddProductItemForm form) {
    ProductEntity product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));

    if (
        product.getProductItems().stream().anyMatch(item -> item.getName().equals(form.getName()))
    ) {
      throw new CustomException(SAME_ITEM_NAME);
    }

    ProductItemEntity productItem = ProductItemEntity.of(sellerId, form);
    product.getProductItems().add(productItem);

    return product;
  }
}