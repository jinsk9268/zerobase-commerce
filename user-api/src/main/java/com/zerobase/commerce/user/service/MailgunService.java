package com.zerobase.commerce.user.service;

import com.zerobase.commerce.user.client.MailgunClient;
import com.zerobase.commerce.user.client.maingun.SendMailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailgunService {

  @Value("${mailgun.key.domain}")
  private String mailgunFrom;
  private final MailgunClient mailgunClient;

  public String sendMail(String email, String subject, String text) {
    SendMailForm form = SendMailForm.builder()
        .from(String.format("zerobase-commerce <mailgun@%s>", mailgunFrom))
        .to(email)
        .subject(subject)
        .text(text)
        .build();

    return mailgunClient.sendMail(form).getBody();
  }
}
