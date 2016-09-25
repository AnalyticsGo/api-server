package com.analyticsgo.service;

import com.analyticsgo.model.User;
import com.analyticsgo.model.UserSession;
import com.analyticsgo.repo.UserSessionRepo;
import com.analyticsgo.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSessionService {

  private final UserSessionRepo userSessionRepo;

  private final TokenGenerator tokenGenerator;

  @Autowired
  public UserSessionService(UserSessionRepo userSessionRepo, TokenGenerator tokenGenerator) {
    this.userSessionRepo = userSessionRepo;
    this.tokenGenerator = tokenGenerator;
  }

  @Transactional(readOnly = true)
  public UserSession findById(String id) {
    return userSessionRepo.findOne(id);
  }

  @Transactional
  public UserSession create(User user, String ip, String userAgent) {
    UserSession userSession = new UserSession();
    userSession.setId(tokenGenerator.createToken());
    userSession.setUser(user);
    userSession.setIp(ip);
    userSession.setUserAgent(userAgent);
    return userSessionRepo.save(userSession);
  }

}
