package com.analyticsgo.repo;

import com.analyticsgo.model.User;
import com.analyticsgo.test.RepoTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class UserRepoTest extends RepoTest {

  public static final String USERNAME = "USERNAME";

  private static final String PASSWORD = "PASSWORD";
  private static final String NEW_PASSWORD = "NEW_PASSWORD";

  @Autowired
  private UserRepo userRepo;

  public static User createUser() {
    User user = new User();
    user.setUsername(USERNAME);
    user.setPasswordHash(PASSWORD);
    return user;
  }

  @Before
  public void setUp() {
    User user = createUser();
    userRepo.save(user);
  }

  @Test
  public void testUsernameShouldBeUnique() {
    thrown.expect(DataIntegrityViolationException.class);
    User user = createUser();
    userRepo.save(user);
  }

  @Test
  public void testUserCreate() {
    User user = userRepo.findByUsername(USERNAME);
    assertThat(user.getUsername(), is(USERNAME));
    assertThat(user.getPasswordHash(), is(PASSWORD));
    assertThat(user.isAdmin(), is(false));
  }

  @Test
  public void testUserUpdate() {
    User user = userRepo.findByUsername(USERNAME);
    user.setPasswordHash(NEW_PASSWORD);
    user.setAdmin(true);
    User updatedUser = userRepo.findByUsername(USERNAME);
    assertThat(updatedUser.getPasswordHash(), is(NEW_PASSWORD));
    assertThat(updatedUser.isAdmin(), is(true));
  }

  @Test
  public void testUserRemove() {
    User user = userRepo.findByUsername(USERNAME);
    userRepo.delete(user);
    User deletedUser = userRepo.findByUsername(USERNAME);
    assertThat(deletedUser, nullValue());
  }

}
