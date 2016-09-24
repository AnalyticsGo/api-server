package com.analyticsgo.service;

import com.analyticsgo.model.User;
import com.analyticsgo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  @Autowired
  private UserRepo userRepo;

  @Transactional(readOnly = true)
  public User findUserByUsername(String username) {
    return userRepo.findByUsername(username);
  }

}
