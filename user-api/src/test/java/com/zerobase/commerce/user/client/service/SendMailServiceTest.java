package com.zerobase.commerce.user.client.service;

import com.zerobase.commerce.user.service.test.SendMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SendMailServiceTest {

  @Autowired
  private SendMailService sendMailService;

  @Test
  void sendMailTest() {
    // given
    // when
    // then
    String response = sendMailService.sendMail();
    System.out.println(response);
  }

}