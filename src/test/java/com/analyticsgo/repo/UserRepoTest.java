package com.analyticsgo.repo;

import com.analyticsgo.model.User;
import com.analyticsgo.test.RepoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserRepoTest extends RepoTest {

  private static final String USERNAME = "userrepotest";
  private static final String PASSWORD = "password";

  @Autowired
  private UserRepo userRepo;

  private User createUser() {
    User user = new User();
    user.setUsername(USERNAME);
    user.setPasswordHash(PASSWORD);
    user.setAdmin(false);
    userRepo.save(user);
    return user;
  }

  @Test
  public void testUserCreate() {
    User user = userRepo.findByUsername(USERNAME);
    assertThat(user, nullValue());
    user = createUser();
    assertThat(user.getId(), notNullValue());
    user = userRepo.findByUsername(USERNAME);
    assertThat(user, notNullValue());
    assertThat(user.getPasswordHash(), is(PASSWORD));
    assertThat(user.isAdmin(), is(false));
  }

  @Test
  public void testUserUpdate() {
    createUser();
    User user = userRepo.findByUsername(USERNAME);
    assertThat(user, notNullValue());
    assertThat(user.getPasswordHash(), is(PASSWORD));
    assertThat(user.isAdmin(), is(false));
    user.setPasswordHash("new_password");
    user.setAdmin(true);
    User updatedUser = userRepo.findByUsername(USERNAME);
    assertThat(updatedUser.getPasswordHash(), is("new_password"));
    assertThat(updatedUser.isAdmin(), is(true));
  }

  @Test
  public void testUserRemove() {
    createUser();
    User user = userRepo.findByUsername(USERNAME);
    assertThat(user, notNullValue());
    userRepo.delete(user);
    User deletedUser = userRepo.findByUsername(USERNAME);
    assertThat(deletedUser, nullValue());
  }

}
