package com.analyticsgo.service;

import com.analyticsgo.model.User;
import com.analyticsgo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional(readOnly = true)
  public User findByUsername(String username) {
    return userRepo.findByUsername(username);
  }

  @Transactional
  public User create(String username, String password, boolean admin) {
    User user = new User();
    user.setUsername(username);
    user.setPasswordHash(passwordEncoder.encode(password));
    user.setAdmin(admin);
    return userRepo.save(user);
  }

}
