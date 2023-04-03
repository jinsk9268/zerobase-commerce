package com.zerobase.commerce.order.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AddProductItemForm {

  private Long productId;
  private String name;
  private Integer price;
  private Integer count;
}
