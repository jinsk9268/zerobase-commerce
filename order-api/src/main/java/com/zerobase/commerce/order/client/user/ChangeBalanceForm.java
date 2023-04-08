package com.zerobase.commerce.order.client.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ChangeBalanceForm {

  private String from;
  private String message;
  private Integer money;
}
