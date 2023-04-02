package com.zerobase.commerce.user.domain.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "customer_balance_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerBalanceHistoryEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(targetEntity = CustomerEntity.class, fetch = FetchType.LAZY)
  private CustomerEntity customer;
  private Integer changeMoney; // 변경된 돈
  private Integer currentBalance; // 해당 시점 잔액
  private String fromMessage;
  private String description;

}
