package com.zerobase.commerce.user.application;

import static com.zerobase.commerce.user.exception.ErrorCode.SIGNIN_CHECK_FAIL;

import com.zerobase.commerce.user.domain.SigninForm;
import com.zerobase.commerce.user.domain.model.CustomerEntity;
import com.zerobase.commerce.user.domain.model.SellerEntity;
import com.zerobase.commerce.user.exception.CustomException;
import com.zerobase.commerce.user.service.customer.CustomerService;
import com.zerobase.commerce.user.service.seller.SellerService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.domain.common.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SigninApplication {

  private final CustomerService customerService;
  private final SellerService sellerService;
  private final JwtAuthenticationProvider provider;

  /**
   * customer
   */
  public String customerSigninToken(SigninForm form) {
    CustomerEntity c = customerService
        .findValidCustomer(form.getEmail(), form.getPassword())
        .orElseThrow(() -> new CustomException(SIGNIN_CHECK_FAIL));

    return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
  }

  /**
   * seller
   */
  public String sellerSigninToken(SigninForm form) {
    SellerEntity s = sellerService
        .findValidSeller(form.getEmail(), form.getPassword())
        .orElseThrow(() -> new CustomException(SIGNIN_CHECK_FAIL));

    return provider.createToken(s.getEmail(), s.getId(), UserType.SELLER);
  }
}
