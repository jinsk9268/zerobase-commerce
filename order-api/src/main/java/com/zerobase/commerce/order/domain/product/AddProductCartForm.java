package com.zerobase.commerce.order.domain.product;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AddProductCartForm {
  private Long id;
  private Long sellerId;
  private String name;
  private String description;
  private List<ProductItem> items;

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Builder
  public static class ProductItem {
    private Long id;
    private String name;
    private Integer count;
    private Integer price;
  }
}
