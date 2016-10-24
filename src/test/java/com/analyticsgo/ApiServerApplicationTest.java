package com.analyticsgo;

import com.analyticsgo.model.User;
import com.analyticsgo.service.ApiKeyService;
import com.analyticsgo.service.UserService;
import com.analyticsgo.service.UserSessionService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServerApplicationTest {

  @Autowired
  private UserService userService;

  @Autowired
  private ApiKeyService apiKeyService;

  @Autowired
  private UserSessionService userSessionService;

  @Test
  @Ignore
  public void setUpTestData() {
    User user = userService.create("userdemo", "Zxcv123$", false);
    apiKeyService.create(user, "First API key");
    userSessionService.create(user, null, null);
  }

}
