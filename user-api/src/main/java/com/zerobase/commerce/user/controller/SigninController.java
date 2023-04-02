package com.zerobase.commerce.user.controller;

import com.zerobase.commerce.user.application.SigninApplication;
import com.zerobase.commerce.user.domain.SigninForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/signin")
@RequiredArgsConstructor
public class SigninController {

  private final SigninApplication signinApplication;

  @PostMapping("/customer")
  public ResponseEntity<String> signinCustomer(@RequestBody SigninForm form) {
    return ResponseEntity.ok(signinApplication.customerSigninToken(form));
  }

  @PostMapping("/seller")
  public ResponseEntity<String> signinSeller(@RequestBody SigninForm form) {
    return ResponseEntity.ok(signinApplication.sellerSigninToken(form));
  }

}
