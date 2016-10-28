package com.analyticsgo.repo;

import com.analyticsgo.model.ApiKey;
import com.analyticsgo.model.User;
import com.analyticsgo.test.RepoTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiKeyRepoTest extends RepoTest {

  private static final String API_KEY_ID = "API_KEY_ID";
  private static final String API_KEY_NAME = "API_KEY_NAME";
  private static final String API_KEY_NEW_NAME = "API_KEY_NEW_NAME";

  @Autowired
  private ApiKeyRepo apiKeyRepo;

  @Autowired
  private UserRepo userRepo;

  @Before
  public void setUp() {
    User user = UserRepoTest.createUser();
    userRepo.save(user);
    ApiKey apiKey = new ApiKey();
    apiKey.setId(API_KEY_ID);
    apiKey.setName(API_KEY_NAME);
    apiKey.setUser(user);
    apiKeyRepo.save(apiKey);
  }

  @Test
  public void testApiKeyCreate() {
    ApiKey apiKey = apiKeyRepo.findOne(API_KEY_ID);
    assertThat(apiKey.getId(), is(API_KEY_ID));
    assertThat(apiKey.getName(), is(API_KEY_NAME));
    assertThat(apiKey.getUser().getUsername(), is(UserRepoTest.USERNAME));
    assertThat(apiKey.isDisabled(), is(false));
  }

  @Test
  public void testApiKeyUpdate() {
    ApiKey apiKey = apiKeyRepo.findOne(API_KEY_ID);
    apiKey.setName(API_KEY_NEW_NAME);
    apiKey.setDisabled(true);
    ApiKey updatedApiKey = apiKeyRepo.findOne(API_KEY_ID);
    assertThat(updatedApiKey.getName(), is(API_KEY_NEW_NAME));
    assertThat(updatedApiKey.isDisabled(), is(true));
    assertThat(updatedApiKey.getUser().getUsername(), is(UserRepoTest.USERNAME));
  }

  @Test
  public void testApiKeyDelete() {
    ApiKey apiKey = apiKeyRepo.findOne(API_KEY_ID);
    apiKeyRepo.delete(apiKey);
    ApiKey deletedApiKey = apiKeyRepo.findOne(API_KEY_ID);
    assertThat(deletedApiKey, nullValue());
    User user = userRepo.findByUsername(UserRepoTest.USERNAME);
    assertThat(user, notNullValue());
  }

  @Test
  public void testFindByUser() {
    User user = userRepo.findByUsername(UserRepoTest.USERNAME);
    List<ApiKey> apiKeys = apiKeyRepo.findByUser(user);
    assertThat(apiKeys, hasSize(1));
    assertThat(apiKeys.get(0).getId(), is(API_KEY_ID));
  }

}
