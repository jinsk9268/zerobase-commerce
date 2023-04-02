package com.zerobase.commerce.user.domain.seller;

import com.zerobase.commerce.user.domain.model.SellerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SellerDto {

  private Long id;
  private String email;

  public static SellerDto from(SellerEntity seller) {
    return new SellerDto(seller.getId(), seller.getEmail());
  }
}
