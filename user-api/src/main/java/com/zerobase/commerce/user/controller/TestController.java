package com.zerobase.commerce.user.controller;

import com.zerobase.commerce.user.service.test.SendMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final SendMailService sendMailService;

  @GetMapping
  public String sendTestMail() {
    return sendMailService.sendMail();
  }

}
