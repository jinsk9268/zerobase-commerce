package com.zerobase.commerce.user.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailgunServiceTest {

  @Autowired
  private MailgunService mailgunService;

  @Test
  void mailgunTest() {
    // given
    // when
    // then
    String response = mailgunService.sendMail(
        "jinsk9268@gmail.com",
        "테스트 subject",
        "테스트 text"
    );

    System.out.println(response);
  }

}