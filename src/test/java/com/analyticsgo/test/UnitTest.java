package com.analyticsgo.test;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public abstract class UnitTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

}
