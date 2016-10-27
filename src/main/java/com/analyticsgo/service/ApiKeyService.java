package com.analyticsgo.service;

import com.analyticsgo.model.ApiKey;
import com.analyticsgo.model.User;
import com.analyticsgo.repo.ApiKeyRepo;
import com.analyticsgo.util.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiKeyService {

  private final ApiKeyRepo apiKeyRepo;
  private final TokenUtils tokenUtils;

  public ApiKeyService(ApiKeyRepo apiKeyRepo, TokenUtils tokenUtils) {
    this.apiKeyRepo = apiKeyRepo;
    this.tokenUtils = tokenUtils;
  }

  @Transactional(readOnly = true)
  public ApiKey findById(String id) {
    return apiKeyRepo.findOne(id);
  }

  @Transactional
  public ApiKey create(User user, String name) {
    ApiKey apiKey = new ApiKey();
    apiKey.setId(tokenUtils.createToken());
    apiKey.setUser(user);
    apiKey.setName(name);
    return apiKeyRepo.save(apiKey);
  }

}
