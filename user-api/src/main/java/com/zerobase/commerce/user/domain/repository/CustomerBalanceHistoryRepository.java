package com.zerobase.commerce.user.domain.repository;

import com.zerobase.commerce.user.domain.model.CustomerBalanceHistoryEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface CustomerBalanceHistoryRepository
    extends JpaRepository<CustomerBalanceHistoryEntity, Long> {

  Optional<CustomerBalanceHistoryEntity> findFirstByCustomer_IdOrderByIdDesc(
      @RequestParam("customer_id") Long customerId
  );
}
