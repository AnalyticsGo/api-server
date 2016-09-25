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

  private final UserService userService;

  private final ApiKeyService apiKeyService;

  private final UserSessionService userSessionService;

  @Autowired
  public ApiServerApplicationTest(UserService userService, ApiKeyService apiKeyService,
      UserSessionService userSessionService) {
    this.userService = userService;
    this.apiKeyService = apiKeyService;
    this.userSessionService = userSessionService;
  }

  @Test
  @Ignore
  public void setUpTestData() {
    User user = userService.create("userdemo", "Zxcv123$", false);
    apiKeyService.create(user, "First API key");
    userSessionService.create(user, null, null);
  }

}
