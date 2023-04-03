package com.zerobase.commerce.order.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UpdateProductItemForm {

  private Long id;
  private String name;
  private Integer price;
  private Integer count;
}
