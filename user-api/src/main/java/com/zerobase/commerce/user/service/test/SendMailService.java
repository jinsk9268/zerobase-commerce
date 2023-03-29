package com.zerobase.commerce.user.service.test;

import com.zerobase.commerce.user.client.MailgunClient;
import com.zerobase.commerce.user.client.maingun.SendMailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMailService {

  @Value("${mailgun.key.domain}")
  private String mailgunFrom;
  private final MailgunClient mailgunClient;

  public String sendMail() {
    SendMailForm form = SendMailForm.builder()
        .from(String.format("joy <mailgun@%s>", mailgunFrom))
        .to("jinsk9268@gmail.com")
        .subject("메일건 테스트")
        .text("메일건 테스트 입니다")
        .build();

    return mailgunClient.sendMail(form).getBody();
  }
}
