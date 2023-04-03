package com.zerobase.commerce.order.domain.product;

import com.zerobase.commerce.order.domain.model.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDto {

  private Long id;
  private String name;
  private String description;
  private List<ProductItemDto> items;

  public static ProductDto from(ProductEntity product) {
    List<ProductItemDto> items = product.getProductItems()
        .stream().map(ProductItemDto::from).collect(Collectors.toList());

    return ProductDto.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .items(items)
        .build();
  }
}
