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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
            productService.addProduct(getSellerId(token), form)
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
            productItemService.addProductItem(getSellerId(token), form)
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
            productService.updateProduct(getSellerId(token), form)
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
            productItemService.updateProductItem(getSellerId(token), form)
        )
    );
  }

  /**
   * 토큰으로 부터 셀러 id 가져오기
   */
  private Long getSellerId(String token) {
    return provider.getUserVo(token).getId();
  }
}