package com.zerobase.commerce.order.service;

import static com.zerobase.commerce.order.exception.ErrorCode.NOT_FOUND_ITEM;
import static com.zerobase.commerce.order.exception.ErrorCode.NOT_FOUND_PRODUCT;

import com.zerobase.commerce.order.domain.model.ProductEntity;
import com.zerobase.commerce.order.domain.model.ProductItemEntity;
import com.zerobase.commerce.order.domain.product.AddProductForm;
import com.zerobase.commerce.order.domain.product.UpdateProductForm;
import com.zerobase.commerce.order.domain.product.UpdateProductItemForm;
import com.zerobase.commerce.order.domain.repository.ProductRepository;
import com.zerobase.commerce.order.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  @Transactional
  public ProductEntity addProduct(Long sellerId, AddProductForm form) {
    return productRepository.save(ProductEntity.of(sellerId, form));
  }

  @Transactional
  public ProductEntity updateProduct(Long sellerId, UpdateProductForm form) {
    ProductEntity product = productRepository.findBySellerIdAndId(sellerId, form.getId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));
    product.setName(form.getName());
    product.setDescription(form.getDescription());

    for (UpdateProductItemForm itemForm : form.getItems()) {
      ProductItemEntity item = product.getProductItems().stream()
          .filter(pi -> pi.getId().equals(itemForm.getId()))
          .findFirst()
          .orElseThrow(() -> new CustomException(NOT_FOUND_ITEM));

      item.setName(itemForm.getName());
      item.setPrice(itemForm.getPrice());
      item.setCount(itemForm.getCount());
    }

    return product;
  }

  @Transactional
  public void deleteProduct(Long sellerId, Long productId) {
    ProductEntity product = productRepository.findBySellerIdAndId(sellerId, productId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));

    productRepository.delete(product);
  }
}
