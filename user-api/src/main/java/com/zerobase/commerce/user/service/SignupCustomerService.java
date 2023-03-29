package com.zerobase.commerce.user.service;

import com.zerobase.commerce.user.domain.SignupForm;
import com.zerobase.commerce.user.domain.model.CustomerEntity;
import com.zerobase.commerce.user.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupCustomerService {

  private final CustomerRepository customerRepository;

  public CustomerEntity signup(SignupForm form) {
    return customerRepository.save(CustomerEntity.from(form));
  }

}
