package com.zerobase.commerce.user.application;

import static com.zerobase.commerce.user.exception.ErrorCode.SIGNIN_CHECK_FAIL;

import com.zerobase.commerce.user.domain.SigninForm;
import com.zerobase.commerce.user.domain.model.CustomerEntity;
import com.zerobase.commerce.user.exception.CustomException;
import com.zerobase.commerce.user.service.CustomerService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.domain.common.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SigninApplication {

  private final CustomerService customerService;
  private final JwtAuthenticationProvider provider;

  public String customerSigninToken(SigninForm form) {
    // 1. 로그인 가능 여부
    CustomerEntity c = customerService
        .findValidCustomer(form.getEmail(), form.getPassword())
        .orElseThrow(() -> new CustomException(SIGNIN_CHECK_FAIL));

    // 2. 토큰을 발행하고
    // 3. 토큰을 response 한다
    return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
  }
}
