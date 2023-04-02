package com.zerobase.commerce.user.application;

import com.zerobase.commerce.user.domain.SignupForm;
import com.zerobase.commerce.user.domain.model.CustomerEntity;
import com.zerobase.commerce.user.domain.model.SellerEntity;
import com.zerobase.commerce.user.exception.CustomException;
import com.zerobase.commerce.user.exception.ErrorCode;
import com.zerobase.commerce.user.service.MailgunService;
import com.zerobase.commerce.user.service.customer.SignupCustomerService;
import com.zerobase.commerce.user.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupApplication {

  private final SignupCustomerService signupCustomerService;
  private final SellerService sellerService;
  private final MailgunService mailgunService;

  /**
   * customer
   */
  public String customerSignup(SignupForm form) {
    if (signupCustomerService.isEmailExist(form.getEmail())) {
      throw new CustomException(ErrorCode.ALREADY_REGISTERED_USER);
    }

    CustomerEntity customer = signupCustomerService.signup(form);
    String code = getRandomCode();

    mailgunService.sendMail(
        customer.getEmail(),
        "Customer Signup Verification Email!",
        getVerificationEmailBody(customer.getName(), "customer", customer.getEmail(), code)
    );

    signupCustomerService.changeCustomerValidateEmail(customer.getId(), code);

    return "회원가입에 성공하였습니다.";
  }

  public void customerVerify(String email, String code) {
    signupCustomerService.verifyEmail(email, code);
  }

  /**
   * seller
   */
  public String sellerSignup(SignupForm form) {
    if (sellerService.isEmailExist(form.getEmail())) {
      throw new CustomException(ErrorCode.ALREADY_REGISTERED_USER);
    }

    SellerEntity seller = sellerService.signup(form);
    String code = getRandomCode();

    mailgunService.sendMail(
        seller.getEmail(),
        "Seller Signup Verification Email!",
        getVerificationEmailBody(seller.getName(), "seller", seller.getEmail(), code)
    );

    sellerService.changeSellerValidateEmail(seller.getId(), code);

    return "판매자 회원가입에 성공했습니다.";
  }

  public void sellerVerify(String email, String code) {
    sellerService.verifyEmail(email, code);
  }

  /**
   * 인증 관련
   */
  private String getRandomCode() {
    return RandomStringUtils.random(10, true, true);
  }

  private String getVerificationEmailBody(String name, String type, String email, String code) {
    StringBuilder info = new StringBuilder();

    return info.append("Hello ").append(name).append("! Please Click link for verification.\n\n")
        .append("http://localhost:8081/signup" + type + "/verify?email=")
        .append(email)
        .append("&code=")
        .append(code).toString();
  }
}
