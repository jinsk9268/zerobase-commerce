package com.zerobase.commerce.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  ALREADY_REGISTERED_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
  NOT_FOUND_USER(HttpStatus.NOT_FOUND, "일치하는 회원이 없습니다."),
  ALREADY_VERIFY(HttpStatus.BAD_REQUEST, "이미 인증이 완료된 회원입니다"),
  WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "인증 코드가 일치하지 않습니다. 잘못된 인증 시도 입니다."),
  EXPIRE_CODE(HttpStatus.BAD_REQUEST, "인증 시간이 만료되었습니다.");

  private final HttpStatus httpStatus;
  private final String errorMessage;
}
