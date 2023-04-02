package com.zerobase.commerce.user.service.customer;

import static com.zerobase.commerce.user.exception.ErrorCode.ALREADY_VERIFY;
import static com.zerobase.commerce.user.exception.ErrorCode.EXPIRE_CODE;
import static com.zerobase.commerce.user.exception.ErrorCode.NOT_FOUND_USER;
import static com.zerobase.commerce.user.exception.ErrorCode.WRONG_VERIFICATION;

import com.zerobase.commerce.user.domain.SignupForm;
import com.zerobase.commerce.user.domain.model.CustomerEntity;
import com.zerobase.commerce.user.domain.repository.CustomerRepository;
import com.zerobase.commerce.user.exception.CustomException;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupCustomerService {

  private final CustomerRepository customerRepository;

  public CustomerEntity signup(SignupForm form) {
    return customerRepository.save(CustomerEntity.from(form));
  }

  public boolean isEmailExist(String email) {
    return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
  }

  @Transactional
  public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode) {
    CustomerEntity customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

    customer.setVerificationCode(verificationCode);
    customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));

    return customer.getVerifyExpiredAt();
  }

  @Transactional
  public void verifyEmail(String email, String code) {
    CustomerEntity customer = customerRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

    if (customer.isVerify()) {
      throw new CustomException(ALREADY_VERIFY);
    } else if (!customer.getVerificationCode().equals(code)) {
      throw new CustomException(WRONG_VERIFICATION);
    } else if (customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
      throw new CustomException(EXPIRE_CODE);
    }

    customer.setVerify(true);
  }
}
