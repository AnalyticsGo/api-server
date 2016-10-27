package com.analyticsgo.util;

import com.analyticsgo.test.UnitTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TokenUtilsTest extends UnitTest {

  private static final int TEST_SIZE = 100;

  private List<String> createTokens() {
    TokenUtils tokenUtils = new TokenUtils();
    List<String> tokens = new ArrayList<>();
    for (int i = 0; i < TEST_SIZE; ++i) {
      tokens.add(tokenUtils.createToken());
    }
    return tokens;
  }

  @Test
  public void testTokenShouldHaveFixedLength() {
    List<String> tokens = createTokens();
    for (String token : tokens) {
      assertThat(token.length(), is(32));
    }
  }

  @Test
  public void testTokenShouldBeRandom() {
    Set<String> tokens = new HashSet<>(createTokens());
    assertThat(tokens, hasSize(TEST_SIZE));
  }

  @Test
  public void testTokenShouldRelateToTimeOrder() throws InterruptedException {
    TokenUtils tokenUtils = new TokenUtils();
    String token1 = tokenUtils.createToken();
    Thread.sleep(5);
    String token2 = tokenUtils.createToken();
    Thread.sleep(5);
    String token3 = tokenUtils.createToken();
    assertThat(token1, lessThan(token2));
    assertThat(token2, lessThan(token3));
  }

}
