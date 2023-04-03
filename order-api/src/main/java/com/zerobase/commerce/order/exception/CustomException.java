package com.zerobase.commerce.order.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;
  private final int status;
  private static final ObjectMapper mapper = new ObjectMapper();

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
    this.status = errorCode.getHttpStatus().value();
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Builder
  public static class CustomExceptionResponse {

    private int status;
    private String code;
    private String message;
  }
}
