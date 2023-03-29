package com.zerobase.commerce.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.zerobase.commerce.user.domain.SignupForm;
import com.zerobase.commerce.user.domain.model.CustomerEntity;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SignupCustomerServiceTest {

  @Autowired
  private SignupCustomerService signupCustomerService;

  @Test
  void signupTest() {
    // given
    SignupForm form = SignupForm.builder()
        .email("abc@gmail.com")
        .name("테스트회원")
        .password("1234")
        .birth(LocalDate.now())
        .phone("01012341234")
        .build();

    // when
    CustomerEntity result = signupCustomerService.signup(form);

    // then
    assertEquals(result.getId(), 1);
    assertEquals(result.getEmail(), "abc@gmail.com");
    assertEquals(result.getName(), "테스트회원");
    assertEquals(result.getPassword(), "1234");
    assertEquals(result.getBirth(), LocalDate.now());
    assertEquals(result.getPhone(), "01012341234");
    assertNotNull(result.getCreateAt());
    assertNotNull(result.getModifiedAt());
  }

}