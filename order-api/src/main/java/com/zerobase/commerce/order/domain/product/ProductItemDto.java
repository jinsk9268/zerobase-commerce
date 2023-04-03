package com.zerobase.commerce.order.domain.product;

import com.zerobase.commerce.order.domain.model.ProductItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class ProductItemDto {

  private Long id;
  private String name;
  private Integer price;
  private Integer count;

  public static ProductItemDto from(ProductItemEntity item) {
    return ProductItemDto.builder()
        .id(item.getId())
        .name(item.getName())
        .price(item.getPrice())
        .count(item.getCount())
        .build();
  }
}
