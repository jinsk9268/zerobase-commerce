package com.zerobase.commerce.user.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupForm {
  private String email;
  private String name;
  private String password;
  private LocalDate birth;
  private String phone;
}