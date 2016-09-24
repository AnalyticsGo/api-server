package com.analyticsgo;

import com.analyticsgo.model.ReadToken;
import com.analyticsgo.model.User;
import com.analyticsgo.repo.ReadTokenRepo;
import com.analyticsgo.repo.UserRepo;
import com.analyticsgo.util.TokenGenerator;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServerApplicationTest {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private ReadTokenRepo readTokenRepo;

  @Autowired
  private TokenGenerator tokenGenerator;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  @Ignore
  public void setupTestData() {
    User user = new User();
    user.setUsername("agdemo");
    user.setPasswordHash(passwordEncoder.encode("Zxcv123$"));
    userRepo.save(user);
    ReadToken readToken = new ReadToken();
    readToken.setId(tokenGenerator.createToken());
    readToken.setUser(user);
    readToken.setName("First read token");
    readTokenRepo.save(readToken);
  }

}
