package com.zerobase.commerce.user.client;

import com.zerobase.commerce.user.client.maingun.SendMailForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {

  @PostMapping("${mailgun.key.domain}" + "/messages")
  ResponseEntity<String> sendMail(@SpringQueryMap SendMailForm sendMailForm);
}
