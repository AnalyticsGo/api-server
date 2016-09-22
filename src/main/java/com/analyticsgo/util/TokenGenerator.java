package com.analyticsgo.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

  private static final char[] BASE64_URL_CHARS = { //
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', //
      'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', //
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', //
      'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', //
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_' //
  };

  public String createToken() {
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
    }
    return StringUtils.leftPad(Long.toString(System.currentTimeMillis(), 32), 10, "0")
        + RandomStringUtils.random(22, BASE64_URL_CHARS);
  }

}
