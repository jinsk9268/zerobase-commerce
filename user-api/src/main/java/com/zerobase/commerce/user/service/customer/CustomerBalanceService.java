package com.zerobase.commerce.user.service.customer;

import static com.zerobase.commerce.user.exception.ErrorCode.NOT_ENOUGH_BALANCE;
import static com.zerobase.commerce.user.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.commerce.user.domain.customer.ChangeBalanceForm;
import com.zerobase.commerce.user.domain.model.CustomerBalanceHistoryEntity;
import com.zerobase.commerce.user.domain.repository.CustomerBalanceHistoryRepository;
import com.zerobase.commerce.user.domain.repository.CustomerRepository;
import com.zerobase.commerce.user.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerBalanceService {

  private final CustomerRepository customerRepository;
  private final CustomerBalanceHistoryRepository customerBalanceHistoryRepository;

  @Transactional(noRollbackFor = {CustomException.class})
  public CustomerBalanceHistoryEntity changeBalance(Long customerId, ChangeBalanceForm form)
      throws CustomException {
    CustomerBalanceHistoryEntity history =
        customerBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(customerId)
            .orElse(
                CustomerBalanceHistoryEntity.builder()
                    .changeMoney(0)
                    .currentBalance(0)
                    .customer(
                        customerRepository.findById(customerId)
                            .orElseThrow(() -> new CustomException(NOT_FOUND_USER))
                    )
                    .build()
            );

    if (history.getCurrentBalance() + form.getMoney() < 0) {
      throw new CustomException(NOT_ENOUGH_BALANCE);
    }

    history = CustomerBalanceHistoryEntity.builder()
        .changeMoney(form.getMoney())
        .currentBalance(history.getCurrentBalance() + form.getMoney())
        .description(form.getMessage())
        .fromMessage(form.getFrom())
        .customer(history.getCustomer())
        .build();

    history.getCustomer().setBalance(history.getCurrentBalance());

    return customerBalanceHistoryRepository.save(history);
  }
}
