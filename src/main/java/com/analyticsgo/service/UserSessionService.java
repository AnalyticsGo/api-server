package com.analyticsgo.service;

import com.analyticsgo.model.User;
import com.analyticsgo.model.UserSession;
import com.analyticsgo.model.json.SessionInfo;
import com.analyticsgo.repo.UserSessionRepo;
import com.analyticsgo.util.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSessionService {

  private final UserSessionRepo userSessionRepo;
  private final TokenUtils tokenUtils;

  public UserSessionService(UserSessionRepo userSessionRepo, TokenUtils tokenUtils) {
    this.userSessionRepo = userSessionRepo;
    this.tokenUtils = tokenUtils;
  }

  @Transactional(readOnly = true)
  public UserSession findById(String id) {
    return userSessionRepo.findOne(id);
  }

  @Transactional
  public UserSession create(User user, String ip, String userAgent) {
    UserSession userSession = new UserSession();
    userSession.setId(tokenUtils.createToken());
    userSession.setUser(user);
    SessionInfo info = new SessionInfo();
    info.setIp(ip);
    info.setUserAgent(userAgent);
    userSession.setInfo(info);
    return userSessionRepo.save(userSession);
  }

}
