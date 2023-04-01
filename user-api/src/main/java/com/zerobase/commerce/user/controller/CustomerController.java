package com.zerobase.commerce.user.controller;

import static com.zerobase.commerce.user.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.commerce.user.domain.customer.CustomerDto;
import com.zerobase.commerce.user.domain.model.CustomerEntity;
import com.zerobase.commerce.user.exception.CustomException;
import com.zerobase.commerce.user.service.CustomerService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.domain.common.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
  private  final JwtAuthenticationProvider provider;
  private final CustomerService customerService;

  @GetMapping("/info")
  public ResponseEntity<CustomerDto> getCustomerInfo(
      @RequestHeader(name = "X-AUTH-TOKEN") String token
  ) {
    UserVo vo = provider.getUserVo(token);
    CustomerEntity customer = customerService.findByIdAndEmail(vo.getId(), vo.getEmail())
        .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

    return ResponseEntity.ok(CustomerDto.from(customer));
  }


}
