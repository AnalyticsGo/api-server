package com.analyticsgo.config;

import com.analyticsgo.test.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BeanConfigTest extends UnitTest {

  private static final String PASSWORD = "53Cr3T_P@$SW0rD";

  private PasswordEncoder passwordEncoder;

  @Before
  public void setUp() {
    BeanConfig beanConfig = new BeanConfig();
    passwordEncoder = beanConfig.passwordEncoder();
    // objectMapper will be tested JsonUtilsTest
  }

  @Test
  public void testPasswordHashShouldMatchRawPassword() {
    String passwordHash = passwordEncoder.encode(PASSWORD);
    assertThat(passwordEncoder.matches(PASSWORD, passwordHash), is(true));
  }

  @Test
  public void testPasswordHashShouldBeUnique() {
    String passwordHash1 = passwordEncoder.encode(PASSWORD);
    String passwordHash2 = passwordEncoder.encode(PASSWORD);
    String passwordHash3 = passwordEncoder.encode(PASSWORD);
    assertThat(passwordHash1, not(equalToIgnoringCase(passwordHash2)));
    assertThat(passwordHash2, not(equalToIgnoringCase(passwordHash3)));
    assertThat(passwordHash3, not(equalToIgnoringCase(passwordHash1)));
  }

  @Test
  public void testEncodingPasswordShouldBeSlow() {
    long start = System.currentTimeMillis();
    passwordEncoder.encode(PASSWORD);
    long time = System.currentTimeMillis() - start;
    assertThat(time, greaterThan(200L));
    start = System.currentTimeMillis();
    passwordEncoder.encode(PASSWORD);
    time = System.currentTimeMillis() - start;
    assertThat(time, greaterThan(200L));
  }

}
