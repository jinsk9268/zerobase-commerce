package com.zerobase.commerce.order.controller;

import com.zerobase.commerce.order.domain.product.AddProductForm;
import com.zerobase.commerce.order.domain.product.AddProductItemForm;
import com.zerobase.commerce.order.domain.product.ProductDto;
import com.zerobase.commerce.order.domain.product.ProductItemDto;
import com.zerobase.commerce.order.domain.product.UpdateProductForm;
import com.zerobase.commerce.order.domain.product.UpdateProductItemForm;
import com.zerobase.commerce.order.service.ProductItemService;
import com.zerobase.commerce.order.service.ProductService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {

  private final ProductService productService;
  private final ProductItemService productItemService;
  private final JwtAuthenticationProvider provider;

  /**
   * 상품 및 아이템 추가
   */
  @PostMapping
  public ResponseEntity<ProductDto> addProduct(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody AddProductForm form
  ) {
    return ResponseEntity.ok(
        ProductDto.from(
            productService.addProduct(provider.getUserId(token), form)
        )
    );
  }

  @PostMapping("/item")
  public ResponseEntity<ProductDto> addProductItem(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody AddProductItemForm form
  ) {
    return ResponseEntity.ok(
        ProductDto.from(
            productItemService.addProductItem(provider.getUserId(token), form)
        )
    );
  }

  /**
   * 상품 및 아이템 수정
   */
  @PutMapping
  public ResponseEntity<ProductDto> updateProduct(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody UpdateProductForm form
  ) {
    return ResponseEntity.ok(
        ProductDto.from(
            productService.updateProduct(provider.getUserId(token), form)
        )
    );
  }

  @PutMapping("/item")
  public ResponseEntity<ProductItemDto> updateProductItem(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody UpdateProductItemForm form
  ) {
    return ResponseEntity.ok(
        ProductItemDto.from(
            productItemService.updateProductItem(provider.getUserId(token), form)
        )
    );
  }

  /**
   * 상품, 상품 아이템 삭제
   */
  @DeleteMapping
  public ResponseEntity<String> deleteProduct(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestParam Long id
  ) {
    productService.deleteProduct(provider.getUserId(token), id);
    return ResponseEntity.ok(id + "번 상품이 삭제되었습니다.");
  }

  @DeleteMapping("/item")
  public ResponseEntity<String> deleteProductItem(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestParam Long id
  ) {
    productItemService.deleteProductItem(provider.getUserId(token), id);
    return ResponseEntity.ok(id + "번 상품 아이템이 삭제되었습니다.");
  }
}