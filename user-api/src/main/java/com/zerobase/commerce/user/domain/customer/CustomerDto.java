package com.zerobase.commerce.user.domain.customer;

import com.zerobase.commerce.user.domain.model.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomerDto {

  private Long id;
  private String email;
  private Integer balance;

  public static CustomerDto from(CustomerEntity customer) {
    Integer balance = customer.getBalance();

    return new CustomerDto(
        customer.getId(),
        customer.getEmail(),
        balance == null ? 0 : balance
    );
  }
}
