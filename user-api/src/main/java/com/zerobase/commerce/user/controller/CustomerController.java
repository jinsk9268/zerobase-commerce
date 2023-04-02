package com.zerobase.commerce.user.controller;

import static com.zerobase.commerce.user.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.commerce.user.domain.customer.ChangeBalanceForm;
import com.zerobase.commerce.user.domain.customer.CustomerDto;
import com.zerobase.commerce.user.domain.model.CustomerEntity;
import com.zerobase.commerce.user.exception.CustomException;
import com.zerobase.commerce.user.service.customer.CustomerBalanceService;
import com.zerobase.commerce.user.service.customer.CustomerService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.domain.common.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

  private final JwtAuthenticationProvider provider;
  private final CustomerService customerService;
  private final CustomerBalanceService customerBalanceService;

  @GetMapping("/info")
  public ResponseEntity<CustomerDto> getCustomerInfo(
      @RequestHeader(name = "X-AUTH-TOKEN") String token
  ) {
    UserVo vo = provider.getUserVo(token);
    CustomerEntity customer = customerService.findByIdAndEmail(vo.getId(), vo.getEmail())
        .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

    return ResponseEntity.ok(CustomerDto.from(customer));
  }

  @PostMapping("/balance")
  public ResponseEntity<Integer> changeBalance(
      @RequestHeader(name = "X-AUTH-TOKEN") String token,
      @RequestBody ChangeBalanceForm form
  ) {
    UserVo vo = provider.getUserVo(token);

    return ResponseEntity.ok(
        customerBalanceService.changeBalance(vo.getId(), form).getCurrentBalance()
    );
  }
}
