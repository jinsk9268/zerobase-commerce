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
public class AddProductForm {

  private String name;
  private String description;
  private List<AddProductItemForm> items;
}
