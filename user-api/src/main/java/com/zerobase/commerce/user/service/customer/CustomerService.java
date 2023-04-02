package com.zerobase.commerce.user.service.customer;

import com.zerobase.commerce.user.domain.model.CustomerEntity;
import com.zerobase.commerce.user.domain.repository.CustomerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  // id만 체크해도 무방
  public Optional<CustomerEntity> findByIdAndEmail(Long id, String email) {
    return customerRepository.findById(id)
        .stream().filter(customer -> customer.getEmail().equals(email))
        .findFirst();
  }

  public Optional<CustomerEntity> findValidCustomer(String email, String password) {
    return customerRepository.findByEmail(email)
        .stream().filter(customer -> customer.getPassword().equals(password) && customer.isVerify())
        .findFirst();
  }

}
