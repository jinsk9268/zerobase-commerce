package com.zerobase.domain.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Aes256UtilTest {
  @Test
  void encryptAndDecryptTest() {

      // given
    String encrypt = Aes256Util.encrypt("Hello world");

      // when
      // then
    assertEquals(Aes256Util.decrypt(encrypt), "Hello world");
  }

}