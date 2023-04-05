package com.zerobase.commerce.order.controller;

import com.zerobase.commerce.order.application.CartApplication;
import com.zerobase.commerce.order.domain.product.AddProductCartForm;
import com.zerobase.commerce.order.domain.redis.Cart;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/cart")
@RequiredArgsConstructor
public class CustomerCartController {

  private final CartApplication cartApplication;
  private final JwtAuthenticationProvider provider;

  @PostMapping
  public ResponseEntity<Cart> addCart(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody AddProductCartForm form
  ) {
    return ResponseEntity.ok(
        cartApplication.addCart(provider.getUserVo(token).getId(), form)
    );
  }
}