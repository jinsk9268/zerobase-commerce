package com.zerobase.commerce.user.controller;

import com.zerobase.commerce.user.application.SignupApplication;
import com.zerobase.commerce.user.domain.SignupForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {

  private final SignupApplication signupApplication;

  /**
   * customer
   */
  @PostMapping("/customer")
  public ResponseEntity<String> customerSignup(@RequestBody SignupForm form) {
    return ResponseEntity.ok(signupApplication.customerSignup(form));
  }

  @PutMapping("/customer/verify")
  public ResponseEntity<String> verifyCustomer(String email, String code) {
    signupApplication.customerVerify(email, code);
    return ResponseEntity.ok("인증이 완료되었습니다.");
  }

  /**
   * seller
   */
  @PostMapping("/seller")
  public ResponseEntity<String> sellerSignup(@RequestBody SignupForm form) {
    return ResponseEntity.ok(signupApplication.sellerSignup(form));
  }

  @PutMapping("/seller/verify")
  public ResponseEntity<String> verifySeller(String email, String code) {
    signupApplication.sellerVerify(email, code);
    return ResponseEntity.ok("판매자 인증이 완료되었습니다.");
  }
}
